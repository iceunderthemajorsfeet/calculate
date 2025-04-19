package com.example.demo.repository;

import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class MapToJsonConverter implements AttributeConverter<Map<Integer, Long>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Integer, Long> attribute) {
        try {
            String json = objectMapper.writeValueAsString(attribute);
            System.out.println("Converting to DB: " + json); // Логируем
            return json;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting Map to JSON", e);
        }

    }

    @Override
    public Map<Integer, Long> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData,
                    new TypeReference<Map<Integer, Long>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to Map", e);
        }
    }
}