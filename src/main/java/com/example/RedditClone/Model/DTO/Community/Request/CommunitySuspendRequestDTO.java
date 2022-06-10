package com.example.RedditClone.Model.DTO.Community.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CommunitySuspendRequestDTO {
    @NotBlank(message = "Reason is mandatory")
    @Length(min = 5, message = "Reason must be at least 5 characters.")
    private String suspendedReason;
}
