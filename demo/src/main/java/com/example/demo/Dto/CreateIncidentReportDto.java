package com.example.demo.Dto;

public class CreateIncidentReportDto {

    private int department_id;
    private int report_user_id;
    private int occurred_process_id;
    private int incident_type_id;
    private String incident_detail;
    private String action_taken;

    public CreateIncidentReportDto(int department_id, int report_user_id, int occurred_process_id, int incident_type_id, String incident_detail, String action_taken){
        this.department_id = department_id;
        this.report_user_id = report_user_id;
        this.occurred_process_id = occurred_process_id;
        this.incident_type_id = incident_type_id;
        this.incident_detail = incident_detail;
        this.action_taken = action_taken;
    }

    public int getDepartmentId(){
        return department_id;
    }

    public int getReportUserId(){
        return report_user_id;
    }

    public int getOccurredProcessId(){
        return occurred_process_id;
    }

    public int getIncidentTypeId(){
        return incident_type_id;
    }

    public String getIncidentDetail(){
        return incident_detail;
    }

    public String getActionTaken(){
        return action_taken;
    }
}
