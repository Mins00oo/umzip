package com.ssafy.umzip.domain.help.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostCommentRequestDto {
    private String comment;

    public PostCommentRequestDto(String comment) {
        this.comment = comment;
    }
}
