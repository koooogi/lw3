package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Builder.MissionBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;

public class YAMLParser extends BaseParser {
    
    private YAMLMapper ym;
    
    public YAMLParser() {
        this.ym = new YAMLMapper();
        ym.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
    @Override
    public boolean extension(File file) {
        if (file == null) return false;
        String name = file.getName();
        String l_name = name.toLowerCase();
        return l_name.endsWith(".yaml") || l_name.endsWith(".yml");
    }
    
    @Override
    public void parse(String text, MissionBuilder builder) {
        try {
            Mission mission = ym.readValue(text, Mission.class);
            builder.setParsedMission(mission);
            
        } catch (IOException e) {
            System.err.println("Failed parsing of YAML file: " + e.getMessage());
            throw new RuntimeException("YAML parsing error", e);
        }
    }
}