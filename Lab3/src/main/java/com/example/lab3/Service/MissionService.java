package com.example.lab3.Service;

import com.example.lab3.Model.Mission;
import com.example.lab3.Repository.MissionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Service
public class MissionService {
    
    @Autowired
    private MissionRepository missionRepository;
    
    public Mission saveMission(Mission mission){
        return missionRepository.save(mission);
    }
    
    public List<Mission> getAllMissions(){
        return missionRepository.findAll();
    }
    
    public Optional<Mission> getMissionById(Long id){
        return missionRepository.findById(id);
    }
    
    public Optional<Mission> getMissionByMissionId(String missionId){
        return missionRepository.findByMissionId(missionId);
    }
    
    public void deleteMission(Long id){
        missionRepository.deleteById(id);
    }
    
    public boolean existsById(Long id){
        return missionRepository.existsById(id);
    }
}
