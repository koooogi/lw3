package com.example.lab3.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "mission_additions")
public class Addition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "addition_key")
    private String key;
    
    @Column(name = "addition_value", columnDefinition = "TEXT")
    private String value;
    
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
    
    public Addition() {}
    
    public Addition(String key, String value, Mission mission) {
        this.key = key;
        this.value = value;
        this.mission = mission;
    }
    
    public Long getId(){ 
        return id;
    }
    public void setId(Long id){ 
        this.id = id; 
    }
    
    public String getKey(){ 
        return key; 
    }
    public void setKey(String key){ 
        this.key = key; 
    }
    
    public String getValue(){ 
        return value; 
    }
    public void setValue(String value){ 
        this.value = value; 
    }
    
    public Mission getMission(){ 
        return mission; 
    }
    public void setMission(Mission mission){ 
        this.mission = mission; 
    }
}