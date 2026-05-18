package com.example.lab3.Model;

import com.example.lab3.ENUMs.ThreatLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "curses")
public class Curse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private ThreatLevel threatLevel;

    @OneToOne(mappedBy = "curse")
    @JsonIgnore 
    private Mission mission;
    
    public Curse() {}
    
    public Curse(String name, String threatLevel){
        this.name = name;
        this.threatLevel = ThreatLevel.fromString(threatLevel);
    }
    
    public Curse(String name, ThreatLevel threatLevel){
        this.name = name;
        this.threatLevel = threatLevel;
    }
    
    public Long getId(){ 
        return id; 
    }
    public void setId(Long id){ 
        this.id = id; 
    }
    
    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    
    public ThreatLevel getThreatLevel(){ 
        return threatLevel; 
    }
    
    public void setThreatLevel(ThreatLevel threatLevel){ 
        this.threatLevel = threatLevel; 
    }
    
    public void setThreatLevel(String threatLevel){ 
        this.threatLevel = ThreatLevel.fromString(threatLevel); 
    }
    
    public Mission getMission() {
        return mission;
    }
    
    public void setMission(Mission mission) {
        this.mission = mission;
    }
}