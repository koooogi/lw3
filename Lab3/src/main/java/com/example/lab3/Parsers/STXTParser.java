package com.example.lab3.Parsers;

import com.example.lab3.Builder.MissionBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class STXTParser extends BaseParser{

    @Override
    public boolean extension(File file) {
        if(file == null){
            return false;
        }
        String name = file.getName();
        String l_name = name.toLowerCase();
        
        if(!l_name.endsWith("txt")){
            return false;
        }
        
        try{
            String text = Files.readString(file.toPath());
            String lines[] = text.split("\\n");
            
            for(String line : lines){
                if(!line.isEmpty()){
                    return line.startsWith("[");
                }
            }
            return false;
        }catch(IOException e){
            return false;
        }
    }
    
    @Override
    public void parse(String text, MissionBuilder builder) {
        
        String[] lines = text.split("\\n");
        
        List<String> missionInfo = new ArrayList<>();
        List<String> curseInfo = new ArrayList<>();
        List<String> sorcererInfo = new ArrayList<>();
        List<String> techniqueInfo = new ArrayList<>();
        Map<String, List<String>> otherInfo = new HashMap<>();
        
        String section = null;
        
        for(String line : lines){
            
            line = line.trim();
            
            if(line.startsWith("[") && line.endsWith("]")){
                section = line.substring(1, line.length() - 1).toUpperCase();
                continue;
            }
            
            if(section != null){
                
                switch(section){
                    case "MISSION" : 
                        missionInfo.add(line);
                        break;
                    case "CURSE" : 
                        curseInfo.add(line);
                        break;
                    case "TECHNIQUE" : 
                        techniqueInfo.add(line);
                        break;
                    case "SORCERER" : 
                        sorcererInfo.add(line);
                        break;
                    default:
                        otherInfo.computeIfAbsent(section, k -> new ArrayList<>()).add(line);
                }
            }
        }
        
        parseMission(missionInfo, builder);
        parseCurse(curseInfo, builder);
        parseTechnique(techniqueInfo, builder);
        parseSorcerer(sorcererInfo, builder);
        parseOther(otherInfo, builder);
    }
    
    public void parseMission(List<String> lines, MissionBuilder builder){
        
        Map<String, String> info = new HashMap<>();
        
        for(String line : lines){
            String parts[] = line.split("=", 2);
            if(parts.length == 2){
                info.put(parts[0], parts[1]);
            }
        }
        validateBase(builder, info);
    }
    
    public void parseCurse(List<String> lines, MissionBuilder builder){
        
        String name = null;
        String lvl = null;
        
        for(String line : lines){
            String parts[] = line.split("=", 2);
            if(parts.length == 2){
                String key = parts[0];
                String value = parts[1];
                
                if(key.equals("name")){
                    name = value;
                } else if(key.equals("threatLevel")){
                    lvl = value;
                }
                
            }
        }
        
        validateCurse(builder, name, lvl);
    }
    
    public void parseTechnique(List<String> lines, MissionBuilder builder){
        
        List<List<String>> blocks = separate(lines);
        Map<Integer, Map<String, String>> info = new HashMap<>();
        int index = 0;
        
        for(List<String> block : blocks){
            
            Map<String, String> data = new HashMap<>();
            for(String line: block){
                String parts[] = line.split("=", 2);
                if(parts.length == 2){
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            if(!data.isEmpty()){
                info.put(index++, data);
            }
        }
        
        validateTechnique(builder, info);
    }
    
    public void parseSorcerer(List<String> lines, MissionBuilder builder){
        
        List<List<String>> blocks = separate(lines);
        Map<Integer, Map<String, String>> info = new HashMap<>();
        int index = 0;
        
        for(List<String> block : blocks){
            
            Map<String, String> data = new HashMap<>();
            for(String line: block){
                String parts[] = line.split("=", 2);
                if(parts.length == 2){
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            if(!data.isEmpty()){
                info.put(index++, data);
            }
        }
        
        validateSorcerer(builder, info);
    }
        
    public void parseOther(Map<String, List<String>> sections, MissionBuilder builder){
        
        for(Map.Entry<String, List<String>> entry : sections.entrySet()){
            String name = entry.getKey();
            List<String> lines = entry.getValue();
        
            Map<String, String> data = new HashMap<>();
        
            for(String line : lines){
                String parts[] = line.split("=", 2);
                if(parts.length == 2){
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
        
            if(!data.isEmpty()){
                builder.putAddition(name, data);
            }
        }
        
    }      
    
    public List<List<String>> separate(List<String> lines){
        
        List<List<String>> blocks = new ArrayList<>();
        List<String> current = new ArrayList<>();
        
        for(String line : lines){
            
            if(line.isEmpty()){
               if(!current.isEmpty()){
                   blocks.add(current);
                   current = new ArrayList<>();
               } 
            }else{
                current.add(line);
            }
        }
        
        if(!current.isEmpty()){
            blocks.add(current);
        }
        
        return blocks;
    }
    
}
