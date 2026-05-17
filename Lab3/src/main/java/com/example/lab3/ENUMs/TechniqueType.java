package com.example.lab3.ENUMs;

public enum TechniqueType {
    
    INNATE,
    SHIKIGAMI,
    BODY,
    WEAPON,
    UNKNOWN;
    
    public static TechniqueType fromString(String value){
        
        if(value == null){
            return UNKNOWN;
        }
        
        switch(value.toUpperCase()){
            
            case "INNATE" : return INNATE;
            case "SHIKIGAMI" : return SHIKIGAMI;
            case "BODY" : return BODY;
            case "WEAPON" : return WEAPON;
            default: return UNKNOWN;
        }
    }
}
