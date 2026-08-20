package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.CreateIncidentReportDto;
import com.example.demo.Repository.CreateIncidentReportRepository;

@Service
public class CreateIncidentReportService {
    private  CreateIncidentReportRepository repository;

    public CreateIncidentReportService(CreateIncidentReportRepository repository){
        this.repository = repository;
    }

    public int createIncidentReport(CreateIncidentReportDto dto){
        return repository.createIncidentReport(dto);
    }
}
