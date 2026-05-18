package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Builder.MissionBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.File;
import java.io.IOException;

public class JSONParser extends BaseParser {
    
    private ObjectMapper om;
    
    public JSONParser() {
        this.om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
    @Override
    public boolean extension(File file) {
        if (file == null) return false;
        String name = file.getName();
        String l_name = name.toLowerCase();
        return l_name.endsWith(".json");
    }
    
    @Override
    public void parse(String text, MissionBuilder builder){
        try{
            Mission mission = om.readValue(text, Mission.class);
            normalizeMissionFields(mission);
            builder.setParsedMission(mission);
            
        }catch (IOException e) {
            System.err.println("Failed parsing of JSON file: " + e.getMessage());
            throw new RuntimeException("JSON parsing error", e);
        }
    }
}