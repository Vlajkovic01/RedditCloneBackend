package com.example.RedditClone.model.dto.report.request;

import com.example.RedditClone.model.enumeration.ReportReason;
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
    private Integer communityId;
}
