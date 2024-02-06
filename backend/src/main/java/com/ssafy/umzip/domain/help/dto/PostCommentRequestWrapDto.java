package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCommentRequestWrapDto {

    private Long boardId;
    private Long memberId;
    private String comment;

    @Builder
    public PostCommentRequestWrapDto(Long boardId, Long memberId, String comment) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.comment = comment;
    }

    public BoardHelpComment toEntity(PostCommentRequestWrapDto requestDto,
                                     BoardHelp boardHelp,
                                     Member member) {
        return BoardHelpComment.builder()
                .boardHelp(boardHelp)
                .member(member)
                .content(requestDto.comment)
                .build();
    }

}
