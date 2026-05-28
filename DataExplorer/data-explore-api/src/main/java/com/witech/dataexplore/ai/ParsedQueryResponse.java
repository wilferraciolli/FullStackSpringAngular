package com.witech.dataexplore.ai;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedQueryResponse {
    private String area;
    private List<String> fieldKeys;
    private List<FilterItem> filters;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilterItem {
        private String fieldKey;
        private String operator;
        private String value;
    }
}
