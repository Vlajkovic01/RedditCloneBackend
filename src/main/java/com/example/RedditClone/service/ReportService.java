package com.example.RedditClone.service;

import com.example.RedditClone.model.dto.report.request.ReportCreateRequestDTO;
import com.example.RedditClone.model.entity.Report;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReportService {

    Report createReport(ReportCreateRequestDTO reportPostRequestDTO, Authentication authentication);
    Report acceptReport(Integer id);
    List<Report> findAllByCommunityId(Integer id);
}
