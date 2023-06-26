package com.example.RedditClone.model.dto.community.request;

import com.example.RedditClone.model.dto.flair.response.FlairGetAllResponseDTO;
import com.example.RedditClone.model.dto.pdf.PDFResponseDTO;
import com.example.RedditClone.model.dto.rule.response.RuleGetByCommunityResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CommunityCreateRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 100, message = "Description must be between 3 and 100 characters.")
    private String description;
    private Set<FlairGetAllResponseDTO> flairs = new HashSet<>();
    private Set<RuleGetByCommunityResponseDTO> rules = new HashSet<>();
    private PDFResponseDTO pdf;
}
