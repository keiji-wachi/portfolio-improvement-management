package com.example.demo.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.CreateIncidentReportDto;
import com.example.demo.Dto.IncidentReportResponseDto;
import com.example.demo.Service.CreateIncidentReportService;
import com.example.demo.Service.GetIncidentReportService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "http://localhost:5173")

public class IncidentReportController {
    private final CreateIncidentReportService createIncidentReportService;
    private final GetIncidentReportService getIncidentReportService;

    public IncidentReportController(CreateIncidentReportService createIncidentReportService, GetIncidentReportService getIncidentReportService){
        this.createIncidentReportService = createIncidentReportService;
        this.getIncidentReportService = getIncidentReportService;
    }

    @PostMapping
    public ResponseEntity<String> createIncidentReport(@RequestBody CreateIncidentReportDto dto) {        
        createIncidentReportService.createIncidentReport(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("異常対応入力完了");
    }

    @GetMapping
    public List<IncidentReportResponseDto> getIncidentReport(@RequestParam YearMonth targetMonth) {
        return getIncidentReportService.searchByMonth(targetMonth);
    }
    
    
}


