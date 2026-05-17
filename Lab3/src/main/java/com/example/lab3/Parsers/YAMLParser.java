package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Builder.MissionBuilder;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YAMLParser extends BaseParser{
    
    private YAMLMapper ym;
    
    public YAMLParser(){
        this.ym = new YAMLMapper();
    }
    
    @Override
    public boolean extension(File file){
        if(file == null){
            return false;
        }
        String name = file.getName();
        String l_name = name.toLowerCase();
        return l_name.endsWith(".yaml");
    }

    @Override
    public void parse(String text, MissionBuilder builder) {
        
        try{
                    
            Mission mission0 = ym.readValue(text, Mission.class);
            
            //BASE
            Map<String, String> infoBase = new HashMap<>();
            infoBase.put("missionId", mission0.getMissionId());
            infoBase.put("date", mission0.getDate());
            infoBase.put("location", mission0.getLocation());
            infoBase.put("outcome", mission0.getOutcome().name());
            infoBase.put("damagecost", String.valueOf(mission0.getDamageCost()));
            infoBase.put("note", mission0.getNote());
            infoBase.put("comment", mission0.getComment());
            
            validateBase(builder, infoBase);
            
            //CURSE
            validateCurse(builder, mission0.getCurse().getName(), mission0.getCurse().getThreatLevel().name());
            
            //SORCERER
            Map<Integer, Map<String, String>> infoSor = new HashMap<>();
            if (mission0.getSorcerers() != null) {
                for (int i = 0; i < mission0.getSorcerers().size(); i++) {
                    Sorcerer s = mission0.getSorcerers().get(i);
                    Map<String, String> d = new HashMap<>();
                    d.put("name", s.getName());
                    d.put("rank", s.getRank().name());
                    infoSor.put(i, d);
                }
            }
            validateSorcerer(builder, infoSor);
            
            //TECHNIQUE
            Map<Integer, Map<String, String>> infoTech = new HashMap<>();
            if (mission0.getTechniques() != null) {
                for (int i = 0; i < mission0.getTechniques().size(); i++) {
                    Technique t = mission0.getTechniques().get(i);
                    Map<String, String> data = new HashMap<>();
                    data.put("name", t.getName());
                    data.put("type", t.getType().name());
                    data.put("owner", t.getOwnerName());
                    data.put("damage", String.valueOf(t.getDamage()));
                    infoTech.put(i, data);
                }
            }
            validateTechnique(builder, infoTech);
            
            //ADDITION
            if(mission0.getAdditions() != null && !mission0.getAdditions().isEmpty()){
                for(Map.Entry<String, Object> entry : mission0.getAdditions().entrySet()){
                builder.putAddition(entry.getKey(), entry.getValue());
            }
        }
        }catch(IOException e){
            System.err.println("Failed parsing of YAML file" + e.getMessage());
        }
    }
}
