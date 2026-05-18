package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Builder.MissionBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;

public class XMLParser extends BaseParser {
    
    private XmlMapper xm;
    
    public XMLParser() {
        this.xm = new XmlMapper();
        xm.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
    @Override
    public boolean extension(File file) {
        if (file == null) return false;
        String name = file.getName();
        String l_name = name.toLowerCase();
        return l_name.endsWith(".xml");
    }
    
    @Override
    public void parse(String text, MissionBuilder builder) {
        try {
            Mission mission = xm.readValue(text, Mission.class);
            
            builder.setParsedMission(mission);
            
        } catch (IOException e) {
            System.err.println("Failed parsing of XML file: " + e.getMessage());
            throw new RuntimeException("XML parsing error", e);
        }
    }
}