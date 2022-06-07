package com.example.RedditClone.Model.DTO.Comment.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDTO {

    @NotBlank(message = "Text is mandatory")
    @Length(min = 3, max = 100, message = "Text must be between 3 and 100 characters.")
    private String text;
    private Integer parentCommentId;
    private Integer postId;
}
