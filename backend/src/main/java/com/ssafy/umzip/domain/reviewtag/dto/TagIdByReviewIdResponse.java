package com.ssafy.umzip.domain.reviewtag.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TagIdByReviewIdResponse {
    private long tagId;
    private String tagName;

    public TagIdByReviewIdResponse(long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
