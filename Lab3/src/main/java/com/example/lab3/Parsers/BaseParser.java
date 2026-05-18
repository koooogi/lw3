package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Model.Curse;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;
import com.example.lab3.Builder.MissionBuilder;
import com.example.lab3.ENUMs.Outcome;
import com.example.lab3.ENUMs.Rank;
import com.example.lab3.ENUMs.TechniqueType;
import com.example.lab3.ENUMs.ThreatLevel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public abstract class BaseParser implements Parsers{

    @Override
    public Mission parse(File file, MissionBuilder builder) throws Exception {
    
    if(file == null || !file.exists()){
        throw new IOException("File not found or does not exist");
    }
    
    if(!extension(file)){
        throw new IOException("Invalid file format. Valid extensions: XML, TXT, JSON");
    }
    
    Path path = Paths.get(file.getPath());
    String text = Files.readString(path);
    parse(text, builder);
    
    return builder.build();
    }
    
    public Sorcerer findSorcerer(MissionBuilder builder, String owner_name){
        if(owner_name == null || owner_name.isEmpty()){
            return new Sorcerer("UNKNOWN", "UNKNOWN");
        }
        
        for(Sorcerer ms: builder.getSorcerers()){
            
            if(owner_name.equals(ms.getName())){
                return ms;
            }
        }
        
        return new Sorcerer(owner_name, "UNKNOWN");
    }
    
    public void validateBase(MissionBuilder builder, Map<String, String> info){
        
        if(info.containsKey("missionid")){
            String id = info.get("missionid");
            if(id != null && !id.isEmpty()){
                builder.setMissionId(id);
            } 
            else {
                builder.setMissionId("EMPTY");
            }
        } 
        else{
            builder.setMissionId("NOT FOUND");
        }
        
        if(info.containsKey("date")){
            String data = info.get("date");
            if(data != null && !data.isEmpty()){
                builder.setDate(data);
            } 
            else {
                builder.setDate("EMPTY");
            }
        } 
        else{
            builder.setDate("NOT FOUND");
        }
        
        if(info.containsKey("location")){
            String loc = info.get("location");
            if(loc != null && !loc.isEmpty()){
                builder.setLocation(loc);
            } 
            else {
                builder.setLocation("EMPTY");
            }
            
        } 
        else{
            builder.setLocation("NOT FOUND");
        }
        
        if(info.containsKey("outcome")){
            String out = info.get("outcome");
            if(out != null && !out.isEmpty()){
                builder.setOutcome(out);
            } 
            else {
                builder.setOutcome("EMPTY");
            }
        } 
        else{
            builder.setOutcome("NOT FOUND");
        }
        
        if(info.containsKey("damagecost")){
            String d = info.get("damagecost");
            if(d != null && !d.isEmpty()){
                builder.setDamageCost(Integer.parseInt(d));
            } 
            else{
                System.err.println("damagecost: empty");
            }
        } 
        else{
                System.err.println("damagecost: not found");
        }
        
        if(info.containsKey("note")){
            String note = info.get("note");
            if (note != null && !note.isEmpty()) {
                builder.setNote(note);
            } 
            else{
                builder.setNote("EMPTY");
            }
        } 
        else{
                builder.setNote("NOT FOUND");
        }
        
        if(info.containsKey("comment")){
             String comment = info.get("comment");
            if(comment != null && !comment.isEmpty()){
                builder.setComment(comment);
            } 
            else{
                builder.setComment("EMPTY");
            }
        } 
        else{
                builder.setComment("NOT FOUND");
        }
        
    }
    
    public void validateTechnique(MissionBuilder builder, Map<Integer, Map<String, String>> info){
        
            for(int i = 0; i < info.size(); i++){
            Map<String, String> d = info.get(i);
            String name = d.get("name");
            String type = d.get("type");
            String owner_name = d.get("owner");
            int damage = 0;
            
            String damage_s = d.get("damage");
            
            if(name != null && !name.isEmpty()){
                if(damage_s != null && !damage_s.isEmpty()){
                try{
                    damage = Integer.parseInt(damage_s);
                }catch(Exception e){
                    System.err.println("Error occured while parsing damage");
                }
            }
            
            if(type == null || type.isEmpty()){
                type = "UNKNOWN";
            }
            
            builder.addTechnique(new Technique(name, type, owner_name, damage));
            }else{
                System.err.println("technique " + i + " has no name");
            }
        }
    }
    
    public void validateCurse(MissionBuilder builder, String name, String lvl){
        
        if(name != null || lvl != null){
            builder.setCurse(new Curse(name != null ? name : "NOT FOUND", lvl != null ? lvl : "NOT FOUND"));
        } 
    }
    
    public void validateSorcerer(MissionBuilder builder, Map<Integer, Map<String, String>> info){
        
        for(int i = 0; i < info.size(); i++){
            Map<String, String> d = info.get(i);
            String name = d.get("name");
            String rank = d.get("rank");
            
            if(name != null && !name.isEmpty()){
                builder.addSorcerer(new Sorcerer(name, rank != null ? rank : "NOT STATED"));
            } 
            else{
                builder.addSorcerer(new Sorcerer("UNKNOWN", rank != null ? rank : "NOT STATED"));
            }
        }
    }
    
    protected void normalizeMissionFields(Mission mission) {
    
        if(mission.getMissionId() == null || mission.getMissionId().isEmpty()) {
            mission.setMissionId("NOT FOUND");
        }
        if(mission.getDate() == null || mission.getDate().isEmpty()) {
            mission.setDate("NOT FOUND");
        }
        if(mission.getLocation() == null || mission.getLocation().isEmpty()) {
            mission.setLocation("NOT FOUND");
        }   
        if(mission.getOutcome() == null) {
            mission.setOutcome(Outcome.UNKNOWN);
        }
        if(mission.getDamageCost() == null) {
            mission.setDamageCost(0);
        }
        if(mission.getNote() == null) {
            mission.setNote("NOT FOUND");
        }
        if(mission.getComment() == null) {
            mission.setComment("NOT FOUND");
        }
    
        if(mission.getCurse() != null) {
            if(mission.getCurse().getName() == null || mission.getCurse().getName().isEmpty()) {
                mission.getCurse().setName("NOT FOUND");
            }
            if(mission.getCurse().getThreatLevel() == null) {
                mission.getCurse().setThreatLevel(ThreatLevel.UNKNOWN);
            }
        } else {
            mission.setCurse(new Curse("NOT FOUND", ThreatLevel.UNKNOWN));
        }
    
        if(mission.getSorcerers() != null) {
            for(Sorcerer s : mission.getSorcerers()) {
                if(s.getName() == null || s.getName().isEmpty()) {
                    s.setName("UNKNOWN");
                }
                if(s.getRank() == null) {
                    s.setRank(Rank.UNKNOWN);
                }
            }
        }
    
        if(mission.getTechniques() != null) {
            for(Technique t : mission.getTechniques()) {
                if(t.getName() == null || t.getName().isEmpty()) {
                    t.setName("UNKNOWN");
                }
                if(t.getType() == null) {
                    t.setType(TechniqueType.UNKNOWN);
                }
                if(t.getOwnerName() == null || t.getOwnerName().isEmpty()) {
                    t.setOwnerName("UNKNOWN");
                }
                if(t.getDamage() == null) {
                    t.setDamage(0);
                }
            }
        }
    }
    
    public abstract void parse(String text, MissionBuilder builder);

    @Override
    public abstract boolean extension(File file);
}
