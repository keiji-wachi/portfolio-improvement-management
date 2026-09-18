package com.example.improvementmanagement.master.dto;

public class IncidentTypeDto {

    private int incidentTypeId;
    private String incidentTypeName;

    public IncidentTypeDto(
            int incidentTypeId,
            String incidentTypeName) {

        this.incidentTypeId = incidentTypeId;
        this.incidentTypeName = incidentTypeName;
    }

    public int getIncidentTypeId() {
        return incidentTypeId;
    }

    public String getIncidentTypeName() {
        return incidentTypeName;
    }
}