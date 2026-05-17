package com.example.lab3.Service;

import com.example.lab3.Builder.MissionBuilder;
import com.example.lab3.Model.Mission;
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

    public Mission parseMissionFile(MultipartFile file) throws Exception{
        
        File tempFile = File.createTempFile("mission_", ".tmp");
        
        try(FileOutputStream fos = new FileOutputStream(tempFile)){
            fos.write(file.getBytes());
        }
        
        Parsers parser = parserGenerator.getParser(tempFile);
        if(parser == null){
            throw new IllegalArgumentException(
                "Unsupported file format: " + file.getOriginalFilename()
            );
        }

        MissionBuilder builder = new MissionBuilder();
        Mission mission = parser.parse(tempFile, builder);
        
        tempFile.delete();
        
        return mission;
    }
}