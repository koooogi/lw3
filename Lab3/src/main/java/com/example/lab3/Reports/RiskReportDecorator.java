package com.example.lab3.Reports;

import com.example.lab3.Model.Mission;
import com.example.lab3.ENUMs.ThreatLevel;

public class RiskReportDecorator extends BaseReportFormatter {
    
    public RiskReportDecorator(ReportFormatter wrapped) {
        super(wrapped);
    }
    
    @Override
    public String format(Mission mission){
        
        StringBuilder sb = new StringBuilder();
        
        String baseReport = super.format(mission);
        sb.append(baseReport);
        
        sb.append("\n").append("*".repeat(50)).append("\n");
        sb.append("RISK ASSESSMENT\n");
        sb.append("*".repeat(50)).append("\n\n");
        
        RiskLevel curseRisk = evaluateCurseRisk(mission);
        sb.append("Curse Risk: ").append(curseRisk.getLevel()).append(" (").append(curseRisk.getScore()).append("/100)\n");
        sb.append("  ").append(curseRisk.getDescription()).append("\n\n");
        
        RiskLevel damageRisk = evaluateDamageRisk(mission);
        sb.append("Financial Risk: ").append(damageRisk.getLevel()).append(" (").append(damageRisk.getScore()).append("/100)\n");
        sb.append("  ").append(damageRisk.getDescription()).append("\n\n");
        
        RiskLevel overallRisk = calculateOverallRisk(curseRisk, damageRisk);
        sb.append("*".repeat(30)).append("\n");
        sb.append("OVERALL RISK: ").append(overallRisk.getLevel()).append("\n");
        sb.append("*".repeat(30)).append("\n");
        sb.append(overallRisk.getRecommendation()).append("\n");
        
        return sb.toString();
    }
    
    private RiskLevel evaluateCurseRisk(Mission mission) {
        if (mission.getCurse() == null) {
            return new RiskLevel("LOW", 0, "No curse detected", "Standard monitoring required");
        }
        
        ThreatLevel threat = mission.getCurse().getThreatLevel();
        switch(threat){
            case SPECIAL_GRADE:
                return new RiskLevel("CRITICAL", 90, 
                    "Special Grade curse detected! Extreme danger!", 
                    "Immediate evacuation and Special Grade sorcerer response required");
            case HIGH:
                return new RiskLevel("HIGH", 70, 
                    "High level curse with significant threat potential", 
                    "Deploy Grade 1 sorcerers, prepare containment barriers");
            case MEDIUM:
                return new RiskLevel("MEDIUM", 40, 
                    "Medium level curse, manageable with proper response", 
                    "Standard curse response protocol");
            default:
                return new RiskLevel("LOW", 15, 
                    "Low threat curse", 
                    "Routine exorcism");
        }
    }
    
    private RiskLevel evaluateDamageRisk(Mission mission) {
        int damage = mission.getDamageCost() != null ? mission.getDamageCost() : 0;
        
        if (damage > 3_000_000) {
            return new RiskLevel("CRITICAL", 85, 
                "Extremely high damage cost: " + String.format("%,d", damage),
                "Review and audit required.");
        } else if (damage > 2_000_000) {
            return new RiskLevel("HIGH", 60, 
                "High damage cost: " + String.format("%,d", damage),
                "Additional protective measures recommended");
        } else if (damage > 1_000_000) {
            return new RiskLevel("MEDIUM", 35, 
                "Moderate damage cost: " + String.format("%,d", damage),
                "Standard damage assessment required");
        } else if (damage > 0) {
            return new RiskLevel("LOW", 15, 
                "Low damage cost: " + String.format("%,d", damage),
                "Minor damage, routine reporting");
        } else {
            return new RiskLevel("MINIMAL", 0, 
                "No significant damage reported",
                "Standard documentation");
        }
    }
    
    private RiskLevel calculateOverallRisk(RiskLevel curseRisk, RiskLevel damageRisk){
        
        int totalScore = (curseRisk.getScore() + damageRisk.getScore()) / 2;
        
        if (totalScore >= 80) {
            return new RiskLevel("CRITICAL", totalScore,
                "CRITICAL RISK LEVEL",
                "IMMEDIATE ACTION REQUIRED!️");
        } else if (totalScore >= 60) {
            return new RiskLevel("HIGH", totalScore,
                "HIGH RISK LEVEL",
                "Urgent response required.");
        } else if (totalScore >= 35) {
            return new RiskLevel("MEDIUM", totalScore,
                "MEDIUM RISK LEVEL",
                "Standard response protocol.");
        } else {
            return new RiskLevel("LOW", totalScore,
                "LOW RISK LEVEL",
                "Routine response protocol.");
        }
    }
    
    private static class RiskLevel {
        private final String level;
        private final int score;
        private final String description;
        private final String recommendation;
        
        public RiskLevel(String level, int score, String description, String recommendation) {
            this.level = level;
            this.score = score;
            this.description = description;
            this.recommendation = recommendation;
        }
        
        public String getLevel(){ 
            return level; 
        }
        public int getScore(){ 
            return score; 
        }
        public String getDescription(){ 
            return description; 
        }
        public String getRecommendation(){ 
            return recommendation; 
        }
    }
}