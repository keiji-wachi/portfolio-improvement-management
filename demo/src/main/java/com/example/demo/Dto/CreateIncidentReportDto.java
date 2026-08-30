package com.example.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateIncidentReportDto {

    @NotNull
    @Positive
    private Integer occurred_process_id;

    @NotNull
    @Positive
    private Integer incident_type_id;

    @NotBlank
    @Size(max = 500)
    private String incident_detail;

    @NotBlank
    @Size(max = 500)
    private String action_taken;

    public CreateIncidentReportDto(Integer occurred_process_id, Integer incident_type_id, String incident_detail, String action_taken){
        this.occurred_process_id = occurred_process_id;
        this.incident_type_id = incident_type_id;
        this.incident_detail = incident_detail;
        this.action_taken = action_taken;
    }

    public Integer getOccurredProcessId(){
        return occurred_process_id;
    }

    public Integer getIncidentTypeId(){
        return incident_type_id;
    }

    public String getIncidentDetail(){
        return incident_detail;
    }

    public String getActionTaken(){
        return action_taken;
    }
}
