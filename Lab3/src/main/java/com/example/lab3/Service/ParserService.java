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
        
        if (parser == null) {
            // Логируем для отладки
            System.out.println("Не удалось найти парсер для файла: " + originalFilename);
            System.out.println("Расширение: " + extension);
            System.out.println("Временный файл: " + tempFile.getAbsolutePath());
            throw new IllegalArgumentException(
                "Unsupported file format: " + originalFilename
            );
        }
        
        MissionBuilder builder = new MissionBuilder();
        Mission mission = parser.parse(tempFile, builder);
        
        tempFile.delete();
        
        return mission;
    }
}