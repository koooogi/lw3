package com.example.lab3.Model;

import com.example.lab3.ENUMs.Outcome;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "missions")
public class Mission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "mission_id")
    @JsonProperty("missionId")
    private String missionId;
    
    private String date;
    private String location;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    @JsonProperty("damageCost")
    private Integer damageCost;
    
    private String note;
    private String comment;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curse_id")
    private Curse curse;
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sorcerer> sorcerers = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Technique> techniques = new ArrayList<>();
    
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addition> additions = new ArrayList<>();
    
    @JsonIgnore
    @Transient
    private Map<String, Object> tempAdditions = new HashMap<>();
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public Mission() {
        this.sorcerers = new ArrayList<>();
        this.techniques = new ArrayList<>();
        this.additions = new ArrayList<>();
        this.tempAdditions = new HashMap<>();
    }
    
    
    public Long getId(){ 
        return id; 
    }
    public void setId(Long id){ 
        this.id = id; 
    }
    
    @JsonProperty("missionId")
    public String getMissionId(){ 
        return missionId; 
    }
    @JsonProperty("missionId")
    public void setMissionId(String missionId){
        this.missionId = missionId; 
    }
    
    public String getDate(){ 
        return date; 
    }
    public void setDate(String date){
        this.date = date; 
    }
    
    public String getLocation(){
        return location; 
    }
    public void setLocation(String location){ 
        this.location = location; 
    }
    
    public Outcome getOutcome(){ 
        return outcome; 
    }
    public void setOutcome(Outcome outcome){ 
        this.outcome = outcome; 
    }
    public void setOutcome(String outcome){ 
        this.outcome = Outcome.fromString(outcome); 
    }
    
    @JsonProperty("damageCost")
    public Integer getDamageCost(){ 
        return damageCost; 
    }
    @JsonProperty("damageCost")
    public void setDamageCost(Integer damageCost){ 
        this.damageCost = damageCost; 
    }
    
    public String getNote(){ 
        return note; 
    }
    public void setNote(String note){
        this.note = note; 
    }
    
    public String getComment(){
        return comment; 
    }
    public void setComment(String comment){ 
        this.comment = comment; 
    }
    
    public Curse getCurse(){ 
        return curse; 
    }
    public void setCurse(Curse curse){ 
        this.curse = curse; 
    }
    
    public List<Sorcerer> getSorcerers(){ 
        return sorcerers; 
    }
    public void setSorcerers(List<Sorcerer> sorcerers){ 
        this.sorcerers = sorcerers; 
    }
    
    public List<Technique> getTechniques(){ 
        return techniques; 
    }
    public void setTechniques(List<Technique> techniques){ 
        this.techniques = techniques; 
    }
    
    public List<Addition> getAdditions(){
        return additions; 
    }
    public void setAdditions(List<Addition> additions){ 
        this.additions = additions; 
    }
    
    @JsonAnySetter
    public void setTempAddition(String key, Object value){
        this.tempAdditions.put(key, value);
    }
    
    @PostLoad
    @PrePersist
    @PreUpdate
    public void convertTempAdditionsToEntities(){
        for(Map.Entry<String, Object> entry : tempAdditions.entrySet()){
            String jsonValue = convertToJson(entry.getValue());
            Addition addition = new Addition(entry.getKey(), jsonValue, this);
            this.additions.add(addition);
        }
        tempAdditions.clear();
    }
    
    public void putAddition(String key, Object value){
        String jsonValue = convertToJson(value);
        Addition addition = new Addition(key, jsonValue, this);
        this.additions.add(addition);
    }
    
    public Object getAddition(String key){
        for(Addition a : additions){
            if(a.getKey().equals(key)){
                return convertFromJson(a.getValue());
            }
        }
        return null;
    }
    
    public Map<String, Object> getAllAdditions(){
        Map<String, Object> result = new HashMap<>();
        for(Addition a : additions){
            result.put(a.getKey(), convertFromJson(a.getValue()));
        }
        return result;
    }
    
    private String convertToJson(Object obj){
        if(obj == null) return null;
        try{
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e){
            return obj.toString();
        }
    }
    
    private Object convertFromJson(String json){
        if (json == null || json.isEmpty()) return null;
        try{
            return mapper.readValue(json, Object.class);
        }catch (JsonProcessingException e){
            return json;
        }
    }
    
    public void addSorcerer(Sorcerer sorcerer){
        sorcerers.add(sorcerer);
        sorcerer.setMission(this);
    }

    public void addTechnique(Technique technique){
        techniques.add(technique);
        technique.setMission(this);
    }
}