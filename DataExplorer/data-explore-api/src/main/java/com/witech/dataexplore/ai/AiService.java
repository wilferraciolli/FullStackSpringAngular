package com.witech.dataexplore.ai;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AiService {

    private static final Logger log = LoggerFactory.getLogger(AiService.class);

    @Value("${ai.groq.api-key:}")
    private String groqApiKey;

    @Value("${ai.groq.url}")
    private String groqUrl;

    @Value("${ai.groq.model}")
    private String groqModel;

    /**
     * Set to true when a corporate firewall performs SSL inspection (MITM).
     * Disables certificate validation for Groq requests only.
     * Configure in application.yml as: ai.groq.trust-all-certs: true
     */
    @Value("${ai.groq.trust-all-certs:false}")
    private boolean trustAllCerts;

    private RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void init() {
        // Spring's spring.config.import may not find .env when the working directory
        // doesn't match the module root (common in IntelliJ). Read both values manually
        // as a fallback so the app works regardless of how it's launched.
        if (groqApiKey == null || groqApiKey.isBlank()) {
            groqApiKey = readFromDotenv("GROQ_API_KEY");
        }
        if (!trustAllCerts) {
            // Allow .env to override the Spring-injected false default
            trustAllCerts = "true".equalsIgnoreCase(readFromDotenv("AI_GROQ_TRUST_ALL_CERTS"));
        }

        restClient = trustAllCerts ? buildTrustAllRestClient() : RestClient.create();

        logStatus();
    }

    /**
     * Builds a RestClient that accepts any SSL certificate.
     * Required for corporate firewalls that perform MITM SSL inspection,
     * which replaces the remote server's certificate with the company's own CA.
     */
    private RestClient buildTrustAllRestClient() {
        log.warn("[AI] trust-all-certs is ENABLED — SSL certificate validation is disabled for Groq requests.");
        try {
            TrustManager[] trustAll = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] c, String a) {}
                    public void checkServerTrusted(X509Certificate[] c, String a) {}
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAll, new SecureRandom());

            HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

            return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("[AI] Failed to create trust-all SSL context, falling back to default", e);
            return RestClient.create();
        }
    }

    /**
     * Tries to read a KEY=VALUE entry from a .env file.
     * Searches in the current working directory first, then one level up
     * (covers IntelliJ running from project root vs module root).
     */
    private String readFromDotenv(String key) {
        String cwd = System.getProperty("user.dir");
        List<String> candidates = List.of(
            cwd + "/.env",                         // module root  (normal case)
            cwd + "/data-explore-api/.env"          // project root (IntelliJ sometimes sets this)
        );

        for (String location : candidates) {
            Path path = Path.of(location);
            if (!Files.exists(path)) continue;

            try {
                Optional<String> found = Files.lines(path)
                    .filter(line -> !line.startsWith("#"))   // skip comments
                    .filter(line -> line.startsWith(key + "="))
                    .map(line -> line.substring(line.indexOf('=') + 1).trim())
                    .filter(v -> !v.isBlank())
                    .findFirst();

                if (found.isPresent()) {
                    log.info("[AI] Loaded {} from {}", key, location);
                    return found.get();
                }
            } catch (IOException e) {
                log.warn("[AI] Could not read {}: {}", location, e.getMessage());
            }
        }
        return "";
    }

    private void logStatus() {
        if (isConfigured()) {
            log.info("[AI] Groq API ready — model: {}, url: {}", groqModel, groqUrl);
        } else {
            log.warn("[AI] GROQ_API_KEY not found — Groq fallback disabled.");
            log.warn("[AI] Working directory searched: {}", System.getProperty("user.dir"));
            log.warn("[AI] Fix: IntelliJ → Edit Run Configuration → Environment variables → GROQ_API_KEY=your_key");
        }
    }

    public boolean isConfigured() {
        return groqApiKey != null && !groqApiKey.isBlank();
    }

    public ParsedQueryResponse parseQuery(ParseQueryRequest request) {
        if (groqApiKey == null || groqApiKey.isBlank()) {
            throw new IllegalStateException(
                "Groq API key not configured. Add GROQ_API_KEY to your .env file");
        }

        String systemPrompt = buildSystemPrompt(request.getSchema());
        var messages = List.of(
            Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user",   "content", request.getPrompt())
        );
        var body = Map.of(
            "model",           groqModel,
            "messages",        messages,
            "response_format", Map.of("type", "json_object")
        );
        @SuppressWarnings("unchecked")
        var response = restClient.post()
            .uri(groqUrl)
            .header("Authorization", "Bearer " + groqApiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .body(Map.class);

        if (response == null) throw new IllegalStateException("Empty response from Groq");
        @SuppressWarnings("unchecked")
        var choices = (List<Map<String, Object>>) response.get("choices");
        @SuppressWarnings("unchecked")
        var message = (Map<String, Object>) choices.getFirst().get("message");
        String content = (String) message.get("content");

        try {
            return objectMapper.readValue(content, ParsedQueryResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse AI JSON response: " + content, e);
        }
    }

    private String buildSystemPrompt(List<ParseQueryRequest.AreaSchema> schema) {
        var sb = new StringBuilder();
        sb.append("You are a query builder assistant. Parse the user's natural language into a structured JSON query.\n\nSchema:\n");
        for (var area : schema) {
            var allFields = area.getFields().stream()
                .map(ParseQueryRequest.FieldSchema::getKey).toList();
            var filterableFields = area.getFields().stream()
                .filter(ParseQueryRequest.FieldSchema::isFilterable)
                .map(f -> f.getKey() + " (" + f.getType() + ")").toList();

            sb.append("Area \"").append(area.getKey())
              .append("\": allFields=[").append(String.join(", ", allFields))
              .append("], filterableFields=[").append(String.join(", ", filterableFields))
              .append("]\n");
        }
        sb.append("""
            Valid operators: equals, not_equals, contains, starts_with, is_null, is_not_null, greater_than, less_than
            Return ONLY a raw JSON object — no explanation, no markdown, no code fences:
            {"area":"<areaKey>","fieldKeys":["<fieldKey>",...],"filters":[{"fieldKey":"<fieldKey>","operator":"<op>","value":"<val>"}]}
            Rules:
            - area must be one of the available area keys
            - fieldKeys must be exact keys from allFields (e.g. "person.email")
            - filters must only use filterable fields
            - value must be empty string for is_null/is_not_null operators
            - include in fieldKeys any fields mentioned in the query plus common identifying fields
            """);
        return sb.toString();
    }
}
