package com.example.RedditClone.controller;

import com.example.RedditClone.service.PDFService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pdf")
public class PDFController {

    private final PDFService pdfService;

    public PDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(value="/{fileName}", produces = {MediaType.APPLICATION_PDF_VALUE})
    public @ResponseBody byte[] downloadPDFFile(@PathVariable String fileName){
        return pdfService.getPdf(fileName);
    }
}
