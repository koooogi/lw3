package com.example.lab3.Controller;

import com.example.lab3.Model.Mission;
import com.example.lab3.Reports.ReportFactory;
import com.example.lab3.Reports.ReportFormatter;
import com.example.lab3.Service.MissionService;
import com.example.lab3.Service.ParserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Missions", description = "API made for mission archieve")
public class MissionController {
    
    @Autowired
    private MissionService missionService;
    @Autowired
    private ParserService parserService;
    
    //UPLOAD MISSION
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload mission from file. Supported extensions: TXT, XML, JSON, YAML")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mission uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Unsupported file format"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<?> uploadMission(@RequestParam("file") MultipartFile file) throws Exception{
        try{
            Mission mission = parserService.parseMissionFile(file);
            Mission saved = missionService.saveMission(mission);
            return ResponseEntity.ok(saved);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error parsing file: " + e.getMessage());
        }
    }
//    
//    //CREATE MISSION FROM JSON
//    @PostMapping
//    @Operation(summary = "Create mission from JSON")
//    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) {
//        Mission saved = missionService.saveMission(mission);
//        return ResponseEntity.ok(saved);
//    }
    
    //GET ALL MISSIONS
    @GetMapping
    @Operation(summary = "Get all missions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Missions extracted successfully"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    public List<Mission> getAllMissions() {
        return missionService.getAllMissions();
    }
    
    //GET MISSION BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get mission by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mission found"),
        @ApiResponse(responseCode = "404", description = "Mission not found")
    })
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        return missionService.getMissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    //GET MISSION BY MISSION_ID
    @GetMapping("/mission-id/{missionId}")
    @Operation(summary = "Get mission by missionId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mission found"),
        @ApiResponse(responseCode = "404", description = "Mission not found")
    })
    public ResponseEntity<Mission> getMissionByMissionId(@PathVariable String missionId) {
        return missionService.getMissionByMissionId(missionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    //DELETE MISSION
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete mission by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mission deleted"),
        @ApiResponse(responseCode = "404", description = "Mission not found")
    })
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        if(!missionService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        missionService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }
    
    //REPORTS
    @GetMapping("/{id}/report")
    @Operation(summary = "Generate mission report. Options: simple, detailed, risk")
    public ResponseEntity<String> generateReport(@PathVariable Long id, @RequestParam(value = "type", defaultValue = "full") String type){
    
        return missionService.getMissionById(id).map(mission -> {
                
            ReportFormatter formatter;
                
                switch(type.toLowerCase()){
                    case "simple":
                        formatter = ReportFactory.createSimpleReport();
                        break;
                    case "detailed":
                        formatter = ReportFactory.createDetailedReport();
                        break;
                    case "risk":
                        formatter = ReportFactory.createRiskReport();
                        break;
                    default:
                        formatter = ReportFactory.createFullReport();
                        break;
                }
                
                String report = formatter.format(mission);
                return ResponseEntity.ok(report);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
