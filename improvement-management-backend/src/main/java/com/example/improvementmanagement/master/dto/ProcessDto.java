package com.example.improvementmanagement.master.dto;

public class ProcessDto {

    private int processId;
    private String processName;

    public ProcessDto(int processId, String processName) {
        this.processId = processId;
        this.processName = processName;
    }

    public int getProcessId() {
        return processId;
    }

    public String getProcessName() {
        return processName;
    }
}

