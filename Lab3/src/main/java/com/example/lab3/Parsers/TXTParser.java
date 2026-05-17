package com.example.lab3.Parsers;

import com.example.lab3.Builder.MissionBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TXTParser extends BaseParser{
    
    private static final Set<String> BASE_KEYS = Set.of(
        "missionid", "date", "location", "outcome", 
        "damagecost", "note", "comment"
    );
    
    @Override
    public boolean extension(File file){
        if(file == null){
            return false;
        }
        String name = file.getName();
        String l_name = name.toLowerCase();
        return l_name.endsWith(".txt");
    }
    
    @Override
    public void parse(String text, MissionBuilder builder){
        
        String parts[] = text.split("\\n");
        
        ArrayList<String> mainInfo = new ArrayList<>();
        ArrayList<String> sorcererInfo = new ArrayList<>();
        ArrayList<String> techniqueInfo = new ArrayList<>();
        ArrayList<String> curseInfo = new ArrayList<>();
        Map<String, List<String>> additionsMap = new HashMap<>();
        
        for(String line : parts){
            
            line = line.trim();
            
            if(line.isEmpty()){
                continue;
            }
            
            if(line.startsWith("curse")){
                curseInfo.add(line);
            }
            else if(line.startsWith("sorcerer")){
                sorcererInfo.add(line);
            }
            else if(line.startsWith("technique")){
                techniqueInfo.add(line);
            } 
            else if(line.contains(":")){
                String key = line.split(":", 2)[0].trim().toLowerCase();
                
                if(BASE_KEYS.contains(key)){
                    mainInfo.add(line);
                }else{
                    String additionKey = key.split("\\[")[0];
                    if(additionKey.contains(".")){
                        additionKey = additionKey.split("\\.")[0];
                    }
                    additionsMap.putIfAbsent(additionKey, new ArrayList<>());
                    additionsMap.get(additionKey).add(line);
                } 
            }
        }
        
        parseMain(mainInfo, builder);
        parseSorcerer(sorcererInfo, builder);
        parseTechnique(techniqueInfo, builder);
        parseCurse(curseInfo, builder);
        parseAdditions(additionsMap, builder);
    }
    
    public void parseMain(ArrayList<String> lines, MissionBuilder builder){
        
        if (lines == null || lines.isEmpty()) return;
        
        Map<String, String> info = new HashMap<>();
        
        for(String line: lines){
            
            if (line.isEmpty()){
                continue;
            }
            
            String parts[] = line.split(":", 2);
            String key = parts[0].trim().toLowerCase();
            String value = parts[1].trim();
            info.put(key, value);
        }
        
        validateBase(builder, info);
    }
    
    public void parseSorcerer(ArrayList<String> lines, MissionBuilder builder){
        
        if(lines == null || lines.isEmpty()) return;
        
        Map<Integer, Map<String, String>> info = new HashMap<>();
        
        for(String line : lines){
            
            if(line.isEmpty()){
                continue;
            }
            
            int start = line.indexOf("[") + 1;
            int end = line.indexOf("]");
            
            int index = Integer.parseInt(line.substring(start, end));
            String scnd = line.substring(end+2);
            String parts[] = scnd.split(":", 2);
            
            String field = parts[0].trim();
            String value = parts[1].trim();
            
            info.putIfAbsent(index, new HashMap<>());
            info.get(index).put(field, value);
        }
        
        validateSorcerer(builder, info);
    }
    
    public void parseTechnique(ArrayList<String> lines, MissionBuilder builder){
        
        if(lines == null || lines.isEmpty()) return;
        
        Map<Integer, Map<String, String>> info = new HashMap<>();
        
        for(String line : lines){
            
            if(line.isEmpty()){
                continue;
            }
            
            int start = line.indexOf("[") + 1;
            int end = line.indexOf("]");
            
            int index = Integer.parseInt(line.substring(start, end));
            String scnd = line.substring(end+2);
            String parts[] = scnd.split(":", 2);
            
            String field = parts[0].trim();
            String value = parts[1].trim();
            
            info.putIfAbsent(index, new HashMap<>());
            info.get(index).put(field, value);
        }
        
        validateTechnique(builder, info);
    }
    
    public void parseCurse(ArrayList<String> lines, MissionBuilder builder){
        
        String name = null;
        String lvl = null;
        
        for(String line: lines){
            
            if(line.isEmpty()){
                continue;
            }
            
            String parts[] = line.split(":", 2);
            String key = parts[0].trim();
            String value = parts[1].trim();
            
            if(key.equals("curse.name")){
                name = value.isEmpty() ? "EMPTY" : value;
            } 
            else if(key.equals("curse.threatLevel")){
                lvl = value.isEmpty() ? "EMPTY" : value;
            }
        }
        
        validateCurse(builder, name, lvl);
    }
    
    private void parseAdditions(Map<String, List<String>> additionsMap, MissionBuilder builder){
        if(additionsMap == null || additionsMap.isEmpty()){
            return;
        } 
        
        for(Map.Entry<String, List<String>> entry : additionsMap.entrySet()){
            String key = entry.getKey();
            List<String> lines = entry.getValue();
            Map<String, String> data = new HashMap<>();
            
            for(String line : lines){
                String[] parts = line.split(":", 2);
                if(parts.length == 2){
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            if(!data.isEmpty()){
                builder.putAddition(key, data);
            }
        }
    }
}
