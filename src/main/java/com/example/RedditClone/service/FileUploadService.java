package com.example.RedditClone.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String saveFile(String uploadDir, MultipartFile multipartFile) throws IOException;
}
