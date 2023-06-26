package com.example.RedditClone.service;

import org.springframework.web.multipart.MultipartFile;

public interface PDFService {

    boolean savePDF(MultipartFile file, String fileName);
    byte[] getPdf(String fileName);
}
