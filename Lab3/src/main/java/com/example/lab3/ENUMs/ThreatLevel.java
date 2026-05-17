package com.example.lab3.ENUMs;

public enum ThreatLevel {
    
    HIGH,
    SPECIAL_GRADE,
    LOW,
    MEDIUM,
    UNKNOWN;
    
    public static ThreatLevel fromString(String value){
        
        if(value == null){
            return UNKNOWN;
        }
        
        switch(value.toUpperCase()){
            case "HIGH" : return HIGH;
            case "SPECIAL_GRADE" : return SPECIAL_GRADE;
            case "LOW" : return LOW;
            case "MEDIUM" : return MEDIUM;
            default: return UNKNOWN;
        }
    }
}
