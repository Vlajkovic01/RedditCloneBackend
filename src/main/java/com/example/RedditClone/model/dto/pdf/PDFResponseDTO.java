package com.example.RedditClone.model.dto.pdf;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PDFResponseDTO {
    private String filename;
    private String pdfText;
}
