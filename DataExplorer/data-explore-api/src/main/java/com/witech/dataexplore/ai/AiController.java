package com.witech.dataexplore.ai;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /** Quick health check — open http://localhost:8080/api/ai/status in a browser to verify config */
    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of(
            "groqConfigured", aiService.isConfigured(),
            "groqModel",      "see application.yml ai.groq.model"
        );
    }

    @PostMapping("/parse-query")
    public ResponseEntity<?> parseQuery(@RequestBody ParseQueryRequest request) {
        try {
            return ResponseEntity.ok(aiService.parseQuery(request));
        } catch (Exception e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getMessage()));
        }
    }
}
