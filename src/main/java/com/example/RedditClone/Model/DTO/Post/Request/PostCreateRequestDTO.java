package com.example.RedditClone.Model.DTO.Post.Request;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
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
}
