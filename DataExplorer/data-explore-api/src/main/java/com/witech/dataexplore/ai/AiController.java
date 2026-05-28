package com.witech.dataexplore.ai;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/parse-query")
    public ResponseEntity<ParsedQueryResponse> parseQuery(@RequestBody ParseQueryRequest request) {
        try {
            return ResponseEntity.ok(aiService.parseQuery(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
