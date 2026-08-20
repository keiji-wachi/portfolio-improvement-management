package com.example.demo.Dto;

import java.time.LocalDateTime;

public class IncidentReportResponseDto {
    private int incidentId;
    private String departmentName;
    private String reportUserName;
    private LocalDateTime reportedAt;
    private String processName;
    private String incidentTypeName;
    private String incidentDetail;
    private String actionTaken;

    public IncidentReportResponseDto(int incidentId, String departmentName, String reportUserName, LocalDateTime reportedAt, String processName, String incidentTypeName, String incidentDetail, String actionTaken){
        this.incidentId = incidentId;
        this.departmentName = departmentName;
        this.reportUserName = reportUserName;
        this.reportedAt = reportedAt;
        this.processName = processName;
        this.incidentTypeName = incidentTypeName;
        this.incidentDetail = incidentDetail;
        this.actionTaken = actionTaken;
    }

    public int getIncidentId(){
        return incidentId;
    }

    public String getDepartmentName(){
        return departmentName;
    }

    public String getReportUserName(){
        return reportUserName;
    }

    public LocalDateTime getReportedAt(){
        return reportedAt;
    }

    public String getProcessName(){
        return processName;
    }

    public String getIncidentTypeName(){
        return incidentTypeName;
    }

        public String getIncidentDetail(){
        return incidentDetail;
    }

    public String getActionTaken(){
        return actionTaken;
    }
}
