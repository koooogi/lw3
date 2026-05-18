package com.example.lab3.DTO;

import com.example.lab3.Model.Curse;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;
import java.util.List;

public class MissionDTO {
    
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private Integer damageCost;
    private String note;
    private String comment;
    private Curse curse;
    private List<Sorcerer> sorcerers;
    private List<Technique> techniques;
    
    public MissionDTO(){}
    
    public String getMissionId(){ 
        return missionId; 
    }
    public void setMissionId(String missionId){ 
        this.missionId = missionId; 
    }
    
    public String getDate(){ 
        return date; }
    public void setDate(String date){ 
        this.date = date; 
    }
    
    public String getLocation(){ 
        return location; 
    }
    public void setLocation(String location){ 
        this.location = location; 
    }
    
    public String getOutcome(){ 
        return outcome; 
    }
    public void setOutcome(String outcome){ 
        this.outcome = outcome; 
    }
    
    public Integer getDamageCost(){ 
        return damageCost; 
    }
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
}