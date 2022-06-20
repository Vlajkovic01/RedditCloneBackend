package com.example.RedditClone.Service.Impl;

import com.example.RedditClone.Service.FileUploadService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final LogService logService;

    public FileUploadServiceImpl(LogService logService) {
        this.logService = logService;
    }

    public String saveFile(String uploadDir,
                            MultipartFile multipartFile) throws IOException {

        logService.message("File upload service, saveFile() method called.", MessageType.INFO);

        String fileName = StringUtils.cleanPath(String.valueOf(System.currentTimeMillis()));
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            logService.message("File upload service, saveFile() method, could not save image.", MessageType.ERROR);
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
