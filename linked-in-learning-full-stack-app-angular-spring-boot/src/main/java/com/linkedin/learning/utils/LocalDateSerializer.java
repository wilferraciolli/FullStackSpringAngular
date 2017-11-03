package com.linkedin.learning.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The type Local date serializer. LocalDate class does not have String argument constructor/factory
 * method hence you have to write your own deserializer to deserialize the Date string representation
 * into LocalDate Object.
 */
public class LocalDateSerializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        //create a date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert the string coming from the client into a data object so it can be serialized.
        String date = p.getValueAsString();

        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }
}
