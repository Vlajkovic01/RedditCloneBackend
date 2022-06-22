package com.example.RedditClone.Controller;

import com.example.RedditClone.Model.DTO.Report.Request.ReportCreateRequestDTO;
import com.example.RedditClone.Model.DTO.Report.Response.ReportGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Report;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Service.ReportService;
import com.example.RedditClone.Util.ExtendedModelMapper;
import com.example.RedditClone.Util.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ExtendedModelMapper modelMapper;
    private final LogService logService;

    public ReportController(LogService logService, ExtendedModelMapper modelMapper, ReportService reportService) {
        this.logService = logService;
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<ReportCreateRequestDTO> createReport(@RequestBody ReportCreateRequestDTO newReport, Authentication authentication){

        logService.message("Report controller, createReport() method called.", MessageType.INFO);

        Report newPostReport = reportService.createReport(newReport, authentication);

        if(newPostReport == null){
            logService.message("Report controller, createReport() method, failed to create a report.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        ReportCreateRequestDTO reportDTO = modelMapper.map(newPostReport, ReportCreateRequestDTO.class);

        return new ResponseEntity<>(reportDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<ReportGetAllResponseDTO> acceptReport(@PathVariable Integer id){

        logService.message("Report controller, acceptReport() method called.", MessageType.INFO);

        Report acceptedReport = reportService.acceptReport(id);

        if(acceptedReport == null){
            logService.message("Report controller, acceptReport() method, failed to create a report.", MessageType.INFO);
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        ReportGetAllResponseDTO reportDTO = modelMapper.map(acceptedReport, ReportGetAllResponseDTO.class);
        return new ResponseEntity<>(reportDTO, HttpStatus.CREATED);
    }
}
