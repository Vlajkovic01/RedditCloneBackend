package com.example.RedditClone.Service;

import com.example.RedditClone.Model.DTO.Report.Request.ReportCreateRequestDTO;
import com.example.RedditClone.Model.Entity.Report;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReportService {

    Report createReport(ReportCreateRequestDTO reportPostRequestDTO, Authentication authentication);
    Report acceptReport(Integer id);
    List<Report> findAllByCommunityId(Integer id);
}
