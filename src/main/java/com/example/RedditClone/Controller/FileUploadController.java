package com.example.RedditClone.Controller;

import com.example.RedditClone.Service.FileUploadService;
import com.example.RedditClone.Service.LogService;
import com.example.RedditClone.Util.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class FileUploadController {

    private final LogService logService;
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService, LogService logService) {
        this.fileUploadService = fileUploadService;
        this.logService = logService;
    }

    @PostMapping("/posts/image")
    public ResponseEntity<String> savePostImg(@RequestParam MultipartFile image) throws IOException {

        logService.message("File upload controller, savePostImg() method called.", MessageType.INFO);

        try{
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

        try{
            String uploadDir = "../RedditCloneFrontend/src/assets/images/user";
            String fileName = fileUploadService.saveFile(uploadDir, image);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception ex) {
            logService.message("File upload controller, saveUserImg() method, bad request.", MessageType.ERROR);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
