package com.example.lab3.Builder;

import com.example.lab3.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionBuilder {
    
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private String note;
    private String comment;
    private int damageCost;
    
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();
    private Curse curse;
    
    private Map<String, Object> tempAdditions = new HashMap<>();
    
    private Mission parsedMission;
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public MissionBuilder setMissionId(String missionId) {
        this.missionId = missionId;
        return this;
    }
    
    public MissionBuilder setDate(String date) {
        this.date = date;
        return this;
    }
    
    public MissionBuilder setLocation(String location) {
        this.location = location;
        return this;
    }
    
    public MissionBuilder setOutcome(String outcome) {
        this.outcome = outcome;
        return this;
    }
    
    public MissionBuilder setNote(String note) {
        this.note = note;
        return this;
    }
    
    public MissionBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }
    
    public MissionBuilder setDamageCost(int damageCost) {
        this.damageCost = damageCost;
        return this;
    }
    
    public MissionBuilder setCurse(Curse curse) {
        this.curse = curse;
        return this;
    }
    
    public MissionBuilder addSorcerer(Sorcerer sorcerer) {
        this.sorcerers.add(sorcerer);
        return this;
    }
    
    public MissionBuilder addTechnique(Technique technique) {
        this.techniques.add(technique);
        return this;
    }
    
    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }
    
    public MissionBuilder setParsedMission(Mission mission) {
        this.parsedMission = mission;
        return this;
    }
    
    public MissionBuilder putAddition(String key, Object value) {
        this.tempAdditions.put(key, value);
        return this;
    }
    
    public Object getAddition(String key) {
        return tempAdditions.get(key);
    }
    
    public Map<String, Object> getAllAdditions() {
        return tempAdditions;
    }
    
    public boolean hasAddition(String key) {
        return tempAdditions.containsKey(key);
    }
    
    public Mission build() {
        if (parsedMission != null) {
            return parsedMission;
        }
        Mission mission = new Mission();
        
        mission.setMissionId(missionId);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setOutcome(outcome);
        mission.setDamageCost(damageCost);
        mission.setNote(note);
        mission.setComment(comment);
        mission.setCurse(curse);
        
        for (Sorcerer s : sorcerers) {
            mission.addSorcerer(s);
        }
        
        for (Technique t : techniques) {
            mission.addTechnique(t);
        }
        
        for (Map.Entry<String, Object> entry : tempAdditions.entrySet()) {
            mission.putAddition(entry.getKey(), entry.getValue());
        }
        
        return mission;
    }
}