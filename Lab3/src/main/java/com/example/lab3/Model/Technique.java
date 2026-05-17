package com.example.lab3.Model;

import com.example.lab3.ENUMs.TechniqueType;
import jakarta.persistence.*;

@Entity
@Table(name = "techniques")
public class Technique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private TechniqueType type;
    
    @Column(name = "owner_name")
    private String ownerName;
    
    @Column(name = "damage_value")
    private Integer damage;
    
    public Technique(){}
    
    public Technique(String name, String type, String ownerName, Integer damage) {
        this.name = name;
        this.type = TechniqueType.fromString(type);
        this.ownerName = ownerName;
        this.damage = damage;
    }

    public Technique(String name, TechniqueType type, String ownerName, Integer damage) {
        this.name = name;
        this.type = type;
        this.ownerName = ownerName;
        this.damage = damage;
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
    
    public TechniqueType getType(){ 
        return type; 
    }

    public void setType(TechniqueType type){ 
        this.type = type; 
    }
    
    public void setType(String type){ 
        this.type = TechniqueType.fromString(type); 
    }
    
    public String getOwnerName(){ 
        return ownerName; 
    }
    public void setOwnerName(String ownerName){ 
        this.ownerName = ownerName; 
    }
    
    public Integer getDamage(){ 
        return damage; 
    }
    public void setDamage(Integer damage){ 
        this.damage = damage; 
    }
}