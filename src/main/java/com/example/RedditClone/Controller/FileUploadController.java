package com.example.RedditClone.Controller;

import com.example.RedditClone.Service.Impl.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/posts/image")
    public ResponseEntity<String> savePostImg(@RequestParam MultipartFile image) throws IOException {
        try{
            String uploadDir = "../RedditCloneFrontend/src/assets/images/post";
            String fileName = fileUploadService.saveFile(uploadDir, image);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/image")
    public ResponseEntity<String> saveUserImg(@RequestParam MultipartFile image) throws IOException {
        try{
            String uploadDir = "../RedditCloneFrontend/src/assets/images/user";
            String fileName = fileUploadService.saveFile(uploadDir, image);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
