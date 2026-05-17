package com.example.lab3.Builder;

import com.example.lab3.Model.Mission;
import com.example.lab3.Model.Curse;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;
import com.example.lab3.ENUMs.Outcome;
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
    
    private Map<String, Object> additions = new HashMap<>();
    
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
    
    public MissionBuilder setOutcome(Outcome outcome) {
        this.outcome = outcome.name();
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
    
    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }
    
    public MissionBuilder addTechnique(Technique technique) {
        this.techniques.add(technique);
        return this;
    }
    
    public List<Technique> getTechniques() {
        return techniques;
    }
    
    public Mission build() {
        Mission mission = new Mission();
        
        mission.setComment(comment);
        mission.setCurse(curse);
        mission.setDamageCost(damageCost);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setMissionId(missionId);
        mission.setNote(note);
        mission.setOutcome(outcome);
        
        for (Sorcerer s : sorcerers) {
            mission.getSorcerers().add(s);
        }
        
        for (Technique t : techniques) {
            mission.getTechniques().add(t);
        }
        
        return mission;
    }
    
    public MissionBuilder putAddition(String key, Object value) {
        this.additions.put(key, value);
        return this;
    }
    
    public Object getAddition(String key) {
        return additions.get(key);
    }
    
    public Map<String, Object> getAdditions() {
        return additions;
    }
    
    public boolean hasAddition(String key) {
        return additions.containsKey(key);
    }
}