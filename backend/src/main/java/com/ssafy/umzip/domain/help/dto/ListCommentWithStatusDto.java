package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListCommentWithStatusDto {
                                    // 댓글과 관련 없는 데이터
    private boolean isSameMember;   // 현재 사용자가 작성자인가?
    private boolean isAdopted;      // 현재 게시글이 채택됐는가?
    private List<ListCommentDto> commentList;

    @Builder
    public ListCommentWithStatusDto(boolean isSameMember, boolean isAdopted, List<ListCommentDto> listDto) {
        this.isSameMember = isSameMember;
        this.isAdopted = isAdopted;
        this.commentList = listDto;
    }
}
