package com.example.lab3.DTO;

import com.example.lab3.Model.Mission;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {
    
    public MissionDTO toDto(Mission mission){
        
        if (mission == null) return null;
        
        MissionDTO dto = new MissionDTO();
        
        dto.setMissionId(mission.getMissionId());
        dto.setDate(mission.getDate());
        dto.setLocation(mission.getLocation());
        dto.setOutcome(mission.getOutcome() != null ? mission.getOutcome().name() : "UNKNOWN");
        dto.setDamageCost(mission.getDamageCost());
        dto.setNote(mission.getNote());
        dto.setComment(mission.getComment());
        dto.setCurse(mission.getCurse());
        dto.setSorcerers(mission.getSorcerers());
        dto.setTechniques(mission.getTechniques());
        
        return dto;
    }
    
    public List<MissionDTO> toDtoList(List<Mission> missions){
        if (missions == null) return null;
        return missions.stream()
            .map(this::toDto)
            .collect(java.util.stream.Collectors.toList());
    }
}