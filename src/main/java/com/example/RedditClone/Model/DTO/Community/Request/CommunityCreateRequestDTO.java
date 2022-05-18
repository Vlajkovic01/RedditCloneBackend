package com.example.RedditClone.Model.DTO.Community.Request;

import com.example.RedditClone.Model.DTO.Flair.Response.FlairGetAllResponseDTO;
import com.example.RedditClone.Model.Entity.Flair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CommunityCreateRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, max = 15, message = "Name must be between 3 and 15 characters.")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 15, message = "Description must be between 3 and 100 characters.")
    private String description;
    @NotEmpty(message = "Flairs is mandatory")
    private Set<FlairGetAllResponseDTO> flairs = new HashSet<>();
}
