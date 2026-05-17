package com.example.lab3.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, Object>, String>{
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute){
        if(attribute == null || attribute.isEmpty()){
            return null;
        }
        try{
            return mapper.writeValueAsString(attribute);
        }catch(JsonProcessingException e){
            throw new RuntimeException("Failed to convert Map to JSON", e);
        }
    }
    
    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData){
        if(dbData == null || dbData.isEmpty()){
            return new HashMap<>();
        }
        try{
            return mapper.readValue(dbData, Map.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Failed to convert JSON to Map", e);
        }
    }
}
