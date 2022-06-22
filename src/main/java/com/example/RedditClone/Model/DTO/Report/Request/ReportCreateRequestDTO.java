package com.example.RedditClone.Model.DTO.Report.Request;

import com.example.RedditClone.Model.Enum.ReportReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportCreateRequestDTO {

    private ReportReason reason;
    private Integer postId;
    private Integer commentId;
}
