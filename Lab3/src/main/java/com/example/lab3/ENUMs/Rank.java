package com.example.lab3.ENUMs;

public enum Rank {
    
    SPECIAL_GRADE,
    GRADE_1,
    GRADE_2,
    SEMI_GRADE_1,
    UNKNOWN;
    
    public static Rank fromString(String value){
        
        if(value == null){
            return UNKNOWN;
        }
        
        switch(value.toUpperCase()){
            case "SPECIAL_GRADE" : return SPECIAL_GRADE;
            case "GRADE_1" : return GRADE_1;
            case "GRADE_2" : return GRADE_2;
            case "SEMI_GRADE_1" : return SEMI_GRADE_1;
            default: return UNKNOWN;
        }
    }
}
