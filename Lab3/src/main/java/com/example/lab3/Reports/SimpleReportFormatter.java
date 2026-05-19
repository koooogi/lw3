package com.example.lab3.Reports;

import com.example.lab3.Model.Mission;

public class SimpleReportFormatter implements ReportFormatter{
    
    @Override
    public String format(Mission mission) {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("*".repeat(50)).append("\n");
        sb.append("MISSION REPORT\n");
        sb.append("*".repeat(50)).append("\n\n");
        
        sb.append("Mission ID: ").append(mission.getMissionId()).append("\n");
        sb.append("Date: ").append(mission.getDate()).append("\n");
        sb.append("Location: ").append(mission.getLocation()).append("\n");
        sb.append("Outcome: ").append(mission.getOutcome()).append("\n");
        sb.append("Damage Cost: ").append(mission.getDamageCost()).append("\n");
        
        return sb.toString();
    }
}