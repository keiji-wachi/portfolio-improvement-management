package com.example.improvementmanagement.incident.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.incident.dto.CreateIncidentReportDto;
import com.example.improvementmanagement.incident.dto.IncidentReportResponseDto;
import com.example.improvementmanagement.incident.service.CreateIncidentReportService;
import com.example.improvementmanagement.incident.service.GetIncidentReportService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
@RequestMapping("/incident")
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")

public class IncidentReportController {
    private final CreateIncidentReportService createIncidentReportService;
    private final GetIncidentReportService getIncidentReportService;

    public IncidentReportController(CreateIncidentReportService createIncidentReportService, GetIncidentReportService getIncidentReportService){
        this.createIncidentReportService = createIncidentReportService;
        this.getIncidentReportService = getIncidentReportService;
    }

    @PostMapping
    public ResponseEntity<String> createIncidentReport(@Valid @RequestBody CreateIncidentReportDto dto, CustomUserDetails loginUser) {      

        createIncidentReportService.createIncidentReport(dto, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("異常対応入力完了");
    }

    @GetMapping
    public List<IncidentReportResponseDto> getIncidentReport(@RequestParam(required = false) YearMonth targetMonth, @AuthenticationPrincipal CustomUserDetails loginUser) {
        return getIncidentReportService.searchByMonth(targetMonth, loginUser);
    }
}


