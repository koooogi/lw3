package com.example.lab3.ENUMs;

public enum Outcome {
    
    SUCCESS,
    FAILURE,
    PARTIAL_SUCCESS,
    UNKNOWN;
    
    public static Outcome fromString(String value){
        
        if(value == null){
            return UNKNOWN;
        }
        
        switch(value.toUpperCase()){
            
            case "SUCCESS" : return SUCCESS;
            case "FAILURE" : return FAILURE;
            case "PARTIAL_SUCCESS" : return PARTIAL_SUCCESS;
            case "UNKNOWN" : return UNKNOWN;
            default: return UNKNOWN;
        }
    }
}
