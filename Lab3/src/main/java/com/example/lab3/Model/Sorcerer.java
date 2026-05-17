package com.example.lab3.Model;

import com.example.lab3.ENUMs.Rank;
import jakarta.persistence.*;

@Entity
@Table(name = "sorcerers")
public class Sorcerer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    public Mission getMission(){ 
        return mission; 
    }
    public void setMission(Mission mission){
        this.mission = mission; 
    }
    
    @Enumerated(EnumType.STRING)
    private Rank rank;
    
    public Sorcerer(){}
    
    public Sorcerer(String name, String rank){
        this.name = name;
        this.rank = Rank.fromString(rank);
    }
    
    public Sorcerer(String name, Rank rank){
        this.name = name;
        this.rank = rank;
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
    
    public Rank getRank(){ 
        return rank; 
    }
    
    public void setRank(Rank rank){ 
        this.rank = rank; 
    }
    
    public void setRank(String rank){ 
        this.rank = Rank.fromString(rank); 
    }
}