package com.example.RedditClone.service.impl;

import com.example.RedditClone.service.PDFService;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class PDFServiceImpl implements PDFService {

    private String path = "../RedditCloneFrontend/src/assets/files/";
    @Override
    public boolean savePDF(MultipartFile file, String fileName) {
        Path dest = Path.of(path, fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public byte[] getPdf(String fileName) {
        try {
            File file = new File(path+fileName);
            InputStream is = new FileInputStream(file);
            return IOUtils.toByteArray(is);

        } catch (IOException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
        }
        return null;
    }
}
