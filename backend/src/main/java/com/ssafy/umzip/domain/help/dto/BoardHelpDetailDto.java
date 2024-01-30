package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardHelpDetailDto {

    private Long writerId;
    private String writerName;

    private Long boardId;
    private String boardTitle;
    private int rewardPoint;
    private Long codeSmallId;
    private String codeSmallName;
    private LocalDateTime boardCreateDt;
    private int boardCommentCnt;
    private int readCnt;

    private List<CommentListDto> commentList = new ArrayList<>();

    private boolean isSameMember;
    private boolean isAdopted;

    @Builder
    BoardHelpDetailDto(BoardHelp boardHelp, List<BoardHelpComment> boardHelpComment,
                       boolean isSameMember) {
        this.writerId = boardHelp.getMember().getId();
        this.writerName = boardHelp.getMember().getName();
        this.boardId = boardHelp.getId();
        this.boardTitle = boardHelp.getTitle();
        this.rewardPoint = boardHelp.getPoint();
        this.codeSmallId = boardHelp.getCodeSmall().getId();
        this.codeSmallName = boardHelp.getCodeSmall().getName();
        this.boardCreateDt = boardHelp.getCreateDt();
        this.boardCommentCnt = boardHelpComment.size();
        this.readCnt = boardHelp.getReadCnt();

        if ( !boardHelpComment.isEmpty() ) {
            this.commentList = CommentListDto.toDto(boardHelpComment);
        }

        this.isSameMember = isSameMember;
        this.isAdopted = boardHelp.getIsAdopted();
    }
}
