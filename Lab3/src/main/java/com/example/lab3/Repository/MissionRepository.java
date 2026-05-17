package com.example.lab3.Repository;

import com.example.lab3.Model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long>{
    Optional<Mission> findByMissionId(String missionId);
}
