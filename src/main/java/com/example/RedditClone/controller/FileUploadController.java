package com.example.RedditClone.controller;

import com.example.RedditClone.lucene.handlers.PDFHandler;
import com.example.RedditClone.model.dto.pdf.PDFResponseDTO;
import com.example.RedditClone.service.FileUploadService;
import com.example.RedditClone.service.LogService;
import com.example.RedditClone.model.enumeration.MessageType;
import com.example.RedditClone.service.PDFService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class FileUploadController {

    private final LogService logService;
    private final FileUploadService fileUploadService;
    private final PDFService pdfService;
    private final PDFHandler pdfHandler;

    public FileUploadController(FileUploadService fileUploadService, LogService logService, PDFService pdfService) {
        this.fileUploadService = fileUploadService;
        this.logService = logService;
        this.pdfService = pdfService;
        this.pdfHandler = new PDFHandler();
    }

    @PostMapping("/posts/image")
    public ResponseEntity<String> savePostImg(@RequestParam MultipartFile image) throws IOException {

        logService.message("File upload controller, savePostImg() method called.", MessageType.INFO);

        try {
            String uploadDir = "../RedditCloneFrontend/src/assets/images/post";
            String fileName = fileUploadService.saveFile(uploadDir, image);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception ex) {
            logService.message("File upload controller, savePostImg() method, bad request.", MessageType.ERROR);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/image")
    public ResponseEntity<String> saveUserImg(@RequestParam MultipartFile image) throws IOException {

        logService.message("File upload controller, saveUserImg() method called.", MessageType.INFO);

        try {
            String uploadDir = "../RedditCloneFrontend/src/assets/images/user";
            String fileName = fileUploadService.saveFile(uploadDir, image);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception ex) {
            logService.message("File upload controller, saveUserImg() method, bad request.", MessageType.ERROR);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<byte[]> getImage(@RequestParam(name = "path") String fileName) throws IOException {

        try {
            String path = "../RedditCloneFrontend/src/" + fileName;
            File imgPath = new File(path);

            byte[] image = Files.readAllBytes(imgPath.toPath());
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (NoSuchFileException | AccessDeniedException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pdf")
    public ResponseEntity<PDFResponseDTO> savePDF(@RequestParam MultipartFile pdf) throws IOException {
        String type = pdf.getOriginalFilename()
                .substring(pdf.getOriginalFilename().lastIndexOf(".")
                        ,pdf.getOriginalFilename().length());
        UUID uuid = UUID.randomUUID();
        String filename = uuid+type;
        if (pdfService.savePDF(pdf, filename)) {
            String pdfText = pdfHandler.getContent(pdf).toString();
            PDFResponseDTO pdfResponse = new PDFResponseDTO(filename, pdfText);
            return new ResponseEntity<>(pdfResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
