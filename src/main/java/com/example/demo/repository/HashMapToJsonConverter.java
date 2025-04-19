package com.example.demo.repository;

//import javax.persistence.*;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public class HashMapToJsonConverter implements AttributeConverter<HashMap<Integer, Long>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(HashMap<Integer, Long> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting HashMap to JSON", e);
        }
    }

    @Override
    public HashMap<Integer, Long> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData,
                    new TypeReference<HashMap<Integer, Long>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to HashMap", e);
        }
    }
}