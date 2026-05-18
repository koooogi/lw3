package com.example.lab3.Reports;

import com.example.lab3.Model.Mission;
import com.example.lab3.Model.Sorcerer;
import com.example.lab3.Model.Technique;

public class DetailedReportDecorator extends BaseReportFormatter{
    
    public DetailedReportDecorator(ReportFormatter wrapped){
        super(wrapped);
    }
    
    @Override
    public String format(Mission mission) {
        StringBuilder sb = new StringBuilder();
        
        String baseReport = super.format(mission);
        sb.append(baseReport);
        
        sb.append("\n").append("*".repeat(50)).append("\n");
        sb.append("DETAILED INFORMATION\n");
        sb.append("*".repeat(50)).append("\n\n");
        
        if (mission.getCurse() != null) {
            sb.append("*** CURSE ***\n");
            sb.append("Name: ").append(mission.getCurse().getName()).append("\n");
            sb.append("Threat Level: ").append(mission.getCurse().getThreatLevel()).append("\n\n");
        }
        
        if (mission.getSorcerers() != null && !mission.getSorcerers().isEmpty()) {
            sb.append("*** SORCERERS ***\n");
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append("* ").append(s.getName()).append(" [").append(s.getRank()).append("]\n");
            }
            sb.append("\n");
        }
        
        if (mission.getTechniques() != null && !mission.getTechniques().isEmpty()) {
            sb.append("*** TECHNIQUES ***\n");
            for (Technique t : mission.getTechniques()) {
                sb.append("* ").append(t.getName()).append(" [").append(t.getType()).append("]\n");
                sb.append("Owner: ").append(t.getOwnerName()).append("\n");
                sb.append("Damage: ").append(t.getDamage()).append("\n");
            }
            sb.append("\n");
        }
        
        if (mission.getNote() != null && !mission.getNote().isEmpty()) {
            sb.append("Note: ").append(mission.getNote()).append("\n");
        }
        if (mission.getComment() != null && !mission.getComment().isEmpty()) {
            sb.append("Comment: ").append(mission.getComment()).append("\n");
        }
        
        return sb.toString();
    }
}