package com.example.RedditClone.lucene.handlers;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PDFHandler {

    private String uploadDir = "../RedditCloneFrontend/src/assets/files/";

    public StringBuffer getContent(MultipartFile file) {
        StringBuffer retVal = new StringBuffer();
        try {
            byte[] fileBytes = file.getBytes();
            File pdFile = new File(file.getOriginalFilename());
            try (OutputStream outputStream = new FileOutputStream(pdFile)) {
                outputStream.write(fileBytes);
            }

            PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(pdFile, "r"));
            parser.parse();
            String text = getText(parser);
            retVal.append(text);

        } catch (IOException e) {
            System.out.println("Error while converting file to pdf");
        }

        return retVal;
    }

    public StringBuffer getContentFromFile(String fileName) {
        StringBuffer retVal = new StringBuffer();
        try {
            File pdFile = new File(uploadDir+fileName);
            PDDocument document = PDDocument.load(pdFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            retVal.append(text);

        } catch (IOException e) {
            System.out.println("Error while converting file to pdf");
        }

        return retVal;
    }


    private String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(parser.getPDDocument());
        } catch (IOException e) {
            System.out.println("Error while converting file to pdf");
        }
        return null;
    }
}
