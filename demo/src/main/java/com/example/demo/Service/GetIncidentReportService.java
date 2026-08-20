package com.example.demo.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.IncidentReportResponseDto;
import com.example.demo.Repository.GetIncidentReportRepository;

@Service
public class GetIncidentReportService {

    private final GetIncidentReportRepository repository;

    public GetIncidentReportService(GetIncidentReportRepository repository){
        this.repository = repository;
    }

    public List<IncidentReportResponseDto> searchByMonth(YearMonth targetMonth){

        LocalDateTime from = targetMonth.atDay(1).atStartOfDay();
        LocalDateTime to = targetMonth.plusMonths(1).atDay(1).atStartOfDay();

        return repository.findByMonth(from, to);
    }
}
