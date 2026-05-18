package com.example.lab3.Reports;

import com.example.lab3.Model.Mission;

public abstract class BaseReportFormatter implements ReportFormatter{
    
    protected ReportFormatter wrapped;
    
    public BaseReportFormatter(ReportFormatter wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public String format(Mission mission) {
        if (wrapped != null) {
            return wrapped.format(mission);
        }
        return "";
    }
}