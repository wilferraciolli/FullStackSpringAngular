package com.linkedin.learning.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Api config.
 */
@Configuration
public class ApiConfig {

    /**
     * Object mapper object mapper. This is to specify how the API will serialise the income JSON.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper objectMapper()    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new ObjectMapper();
    }

    /**
     * Object writer object writer. This is to specify how to deserialize a java object into a JSON response.
     *
     * @param objectMapper the object mapper
     * @return the object writer
     */
    @Bean
    public ObjectWriter objectWriter (ObjectMapper objectMapper){
        return  objectMapper.writerWithDefaultPrettyPrinter();
    }
}
