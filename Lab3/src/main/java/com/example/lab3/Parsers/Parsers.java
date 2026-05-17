package com.example.lab3.Parsers;

import com.example.lab3.Model.Mission;
import com.example.lab3.Builder.MissionBuilder;
import java.io.File;

public interface Parsers {
    Mission parse(File file, MissionBuilder builder) throws Exception;
    boolean extension(File file);
}
