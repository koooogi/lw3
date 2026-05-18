package com.example.lab3.Reports;

public class ReportFactory {
    
    public static ReportFormatter createSimpleReport(){
        return new SimpleReportFormatter();
    }
    
    public static ReportFormatter createDetailedReport(){
        return new DetailedReportDecorator(new SimpleReportFormatter());
    }
    
    public static ReportFormatter createRiskReport(){
        return new RiskReportDecorator(new SimpleReportFormatter());
    }
    
    public static ReportFormatter createFullReport(){
        return new RiskReportDecorator(
            new DetailedReportDecorator(
                new SimpleReportFormatter()
            )
        );
    }
}