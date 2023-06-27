package com.example.RedditClone.model.dto.post.request;

import com.example.RedditClone.model.dto.flair.response.FlairGetAllResponseDTO;
import com.example.RedditClone.model.dto.pdf.PDFResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequestDTO {

    @NotBlank(message = "Title is mandatory")
    @Length(min = 3, max = 20, message = "Title must be between 3 and 20 characters.")
    private String title;
    @NotBlank(message = "Text is mandatory")
    @Length(min = 3, max = 100, message = "Text must be between 3 and 100 characters.")
    private String text;
    private String imagePath;
    private FlairGetAllResponseDTO flair;
    private PDFResponseDTO pdf;
}
