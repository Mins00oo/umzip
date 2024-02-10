package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ListCommentDto {

    private Long boardId;
    private Long writerId;
    private String writerName;
    private String writerImageUrl;
    private String comment;
    private LocalDateTime createDt;

    @Builder
    ListCommentDto(BoardHelpComment boardHelpComment) {
        this.boardId = boardHelpComment.getBoardHelp().getId();
        this.writerId = boardHelpComment.getMember().getId();
        this.writerName = boardHelpComment.getMember().getName();
        this.writerImageUrl = boardHelpComment.getMember().getImageUrl();
        this.comment = boardHelpComment.getContent();
        this.createDt = boardHelpComment.getCreateDt();
    }

    public static List<ListCommentDto> toDto(List<BoardHelpComment> entityList) {

        List<ListCommentDto> list = new ArrayList<>();
        for(BoardHelpComment comment : entityList) {
            list.add(ListCommentDto.builder()
                    .boardHelpComment(comment)
                    .build());
        }

        return list;
    }
}
