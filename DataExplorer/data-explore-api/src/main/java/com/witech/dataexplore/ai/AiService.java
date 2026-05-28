package com.witech.dataexplore.ai;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;


@Service
public class AiService {
    @Value("${ai.groq.api-key:}")
    private String groqApiKey;

    @Value("${ai.groq.url}")
    private String groqUrl;

    @Value("${ai.groq.model}")
    private String groqModel;

    private final RestClient   restClient   = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
