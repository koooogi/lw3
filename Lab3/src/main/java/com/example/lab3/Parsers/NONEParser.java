package com.example.lab3.Parsers;

import com.example.lab3.Builder.MissionBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NONEParser extends BaseParser {

    @Override
    public boolean extension(File file){
        if (file == null) return false;
        String name = file.getName();
        String l_name = name.toLowerCase();
        return !l_name.contains(".");
    }
    
    @Override
    public void parse(String text, MissionBuilder builder){
        
        String[] lines = text.split("\\n");
        
        String missionId = null;
        String date = null;
        String location = null;
        String outcome = null;
        String damageCost = null;
        
        List<Map<String, String>> sorcerersInfo = new ArrayList<>();
        List<Map<String, String>> techniquesInfo = new ArrayList<>();
        Map<String, String> baseInfo = new HashMap<>();
        Map<String, Object> additions = new HashMap<>();
        
        for (String line : lines){
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split("\\|");
            if (parts.length == 0) continue;
            
            String type = parts[0];
            
            switch (type){
                case "MISSION_CREATED":
                    if (parts.length >= 4){
                        missionId = parts[1];
                        date = parts[2];
                        location = parts[3];
                    }
                    break;
                    
                case "CURSE_DETECTED":
                    if (parts.length >= 3){
                        validateCurse(builder, parts[1], parts[2]);
                    }
                    break;
                    
                case "SORCERER_ASSIGNED":
                    if (parts.length >= 3){
                        Map<String, String> sorcerer = new HashMap<>();
                        sorcerer.put("name", parts[1]);
                        sorcerer.put("rank", parts[2]);
                        sorcerersInfo.add(sorcerer);
                    }
                    break;
                    
                case "TECHNIQUE_USED":
                    if (parts.length >= 5){
                        Map<String, String> technique = new HashMap<>();
                        technique.put("name", parts[1]);
                        technique.put("type", parts[2]);
                        technique.put("owner", parts[3]);
                        technique.put("damage", parts[4]);
                        techniquesInfo.add(technique);
                    }
                    break;
                    
                case "MISSION_RESULT":
                    if (parts.length >= 3){
                        outcome = parts[1];
                        String[] kv = parts[2].split("=");
                        if(kv.length == 2 && kv[0].equals("damageCost")) {
                            damageCost = kv[1];
                        }
                    }
                    break;
                    
                default:
                    List<String> values = new ArrayList<>();
                    for(int i = 1; i < parts.length; i++){
                        values.add(parts[i]);
                    }
                    additions.put(type.toLowerCase(), values);
                    break;
            }
        }
        
        if(missionId != null) baseInfo.put("missionid", missionId);
        if(date != null) baseInfo.put("date", date);
        if(location != null) baseInfo.put("location", location);
        if(outcome != null) baseInfo.put("outcome", outcome);
        if(damageCost != null) baseInfo.put("damagecost", damageCost);
        
        if(!baseInfo.isEmpty()){
            validateBase(builder, baseInfo);
        }
        
        if(!sorcerersInfo.isEmpty()){
            Map<Integer, Map<String, String>> sorInfo = new HashMap<>();
            for (int i = 0; i < sorcerersInfo.size(); i++) {
                sorInfo.put(i, sorcerersInfo.get(i));
            }
            validateSorcerer(builder, sorInfo);
        }
        
        if(!techniquesInfo.isEmpty()){
            Map<Integer, Map<String, String>> techInfo = new HashMap<>();
            for(int i = 0; i < techniquesInfo.size(); i++){
                techInfo.put(i, techniquesInfo.get(i));
            }
            validateTechnique(builder, techInfo);
        }
        
        for(Map.Entry<String, Object> entry : additions.entrySet()){
            builder.putAddition(entry.getKey(), entry.getValue());
        }
    }
}