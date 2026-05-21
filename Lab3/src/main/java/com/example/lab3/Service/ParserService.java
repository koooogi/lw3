package com.example.lab3.Service;

import com.example.lab3.Builder.MissionBuilder;
import com.example.lab3.Model.Mission;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;
import com.example.lab3.Parsers.ParserGenerator;
import com.example.lab3.Parsers.Parsers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class ParserService {
    
    private final ParserGenerator parserGenerator;
    
    public ParserService() {
        this.parserGenerator = new ParserGenerator();
    }

    public Mission parseMissionFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if(originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        File tempFile = File.createTempFile("mission_", extension);
        
        try(FileOutputStream fos = new FileOutputStream(tempFile)){
            fos.write(file.getBytes());
        }
        
        Parsers parser = parserGenerator.getParser(tempFile);
        
        
        MissionBuilder builder = new MissionBuilder();
        Mission mission = parser.parse(tempFile, builder);
        
        for(Sorcerer s : mission.getSorcerers()){
            s.setMission(mission);
        }   
        for(Technique t : mission.getTechniques()){
            t.setMission(mission);
        }
        
        tempFile.delete();
        
        return mission;
    }
}