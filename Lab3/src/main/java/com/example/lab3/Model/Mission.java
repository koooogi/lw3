package com.example.lab3.Model;

import com.example.lab3.ENUMs.Outcome;
import com.example.lab3.Converter.JsonMapConverter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    
    @Convert(converter = JsonMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Object> additions = new HashMap<>();
    
    public Mission() {
        this.sorcerers = new ArrayList<>();
        this.techniques = new ArrayList<>();
        this.additions = new HashMap<>();
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
    @JsonProperty("missionId")
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
    
    @JsonAnySetter
    public void setAddition(String key, Object value){
        if(this.additions == null){
            this.additions = new HashMap<>();
        }
        this.additions.put(key, value);
    }
    
    public Map<String, Object> getAdditions(){
        return additions; 
    }
    public void setAdditions(Map<String, Object> additions){ 
        this.additions = additions;
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