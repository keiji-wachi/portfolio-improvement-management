package com.example.demo.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.IncidentReportResponseDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Repository.GetIncidentReportRepository;

@Service
public class GetIncidentReportService {

    private final GetIncidentReportRepository repository;
    private static final int INSTRUCTOR = 2;
    private static final int RELIEF = 3;
    private static final int WORKER = 4;
        
    public GetIncidentReportService(GetIncidentReportRepository repository){
        this.repository = repository;
    }

    public List<IncidentReportResponseDto> searchByMonth(YearMonth targetMonth,LoginResponseDto loginUser) {

        if (loginUser == null) {
            throw new IllegalStateException("ログインが必要です");
        }

        YearMonth searchMonth;

        if (targetMonth == null) {
            searchMonth = YearMonth.now();
        } else {
            searchMonth = targetMonth;
        }

        LocalDateTime from = searchMonth.atDay(1).atStartOfDay();
        LocalDateTime to = searchMonth.plusMonths(1).atDay(1).atStartOfDay();

        int loginRoleId = loginUser.getRoleId();

        if (loginRoleId != INSTRUCTOR && loginRoleId != RELIEF && loginRoleId != WORKER) {
            throw new IllegalStateException("異常対応記録の閲覧権限がありません");
        }

        int departmentId = loginUser.getDepartmentId();

        return repository.findByMonth(from, to, departmentId);
    }
}
