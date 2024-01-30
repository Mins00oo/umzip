package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentListDto {

    private Long writerId;
    private String writerName;
    private String comment;
    private LocalDateTime createDt;

    @Builder
    CommentListDto(BoardHelpComment boardHelpComment) {
        this.writerId = boardHelpComment.getMember().getId();
        this.writerName = boardHelpComment.getMember().getName();
        this.comment = boardHelpComment.getContent();
        this.createDt = boardHelpComment.getCreateDt();
    }

    public static List<CommentListDto> toDto(List<BoardHelpComment> entityList) {

        List<CommentListDto> list = new ArrayList<>();
        for(BoardHelpComment comment : entityList) {
            list.add(CommentListDto.builder()
                    .boardHelpComment(comment)
                    .build());
        }

        return list;
    }
}
