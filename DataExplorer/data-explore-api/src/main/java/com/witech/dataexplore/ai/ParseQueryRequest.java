package com.witech.dataexplore.ai;
import lombok.Data;
import java.util.List;

@Data
public class ParseQueryRequest {
    private String prompt;
    private List<AreaSchema> schema;

    @Data
    public static class AreaSchema {
        private String key;
        private String label;
        private List<FieldSchema> fields;
    }

    @Data
    public static class FieldSchema {
        private String key;
        private String label;
        private String type;
        private boolean filterable;
    }
}
