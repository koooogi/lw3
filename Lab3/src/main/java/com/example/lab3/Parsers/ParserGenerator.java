package com.example.lab3.Parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParserGenerator{
    
    private List<Parsers> parsers = new ArrayList<>();
    
    public ParserGenerator(){ //вопрос к производительности
        parsers.add(new STXTParser());
        parsers.add(new TXTParser());
        parsers.add(new XMLParser());
        parsers.add(new JSONParser());
        parsers.add(new YAMLParser());
        parsers.add(new NONEParser());
    }
    
    public Parsers getParser(File file){
        if(file == null){
            return null;
        }
        for(Parsers p : parsers){
            if(p.extension(file)){
                return p;
            }
        }
        System.out.println("No parser made. Valid extensions: XML, TXT, JSON");
        return null;
    }
}
