package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DetailHelpDto {

    private Long writerId;
    private String writerName;
    private String writerImageUrl;

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private int rewardPoint;
    private Long codeSmallId;
    private String codeSmallName;
    private LocalDateTime boardCreateDt;
    private Long boardCommentCnt;
    private int readCnt;

    private boolean isSameMember;
    private boolean isAdopted;
    private List<String> imagePathList = new ArrayList<>();

    @Builder
    DetailHelpDto(BoardHelp boardHelp, boolean isSameMember, List<String> imagePathList, Long commentCnt) {
        this.writerId = boardHelp.getMember().getId();
        this.writerName = boardHelp.getMember().getName();
        this.writerImageUrl = boardHelp.getMember().getImageUrl();

        this.boardId = boardHelp.getId();
        this.boardTitle = boardHelp.getTitle();
        this.boardContent = boardHelp.getContent();
        this.rewardPoint = boardHelp.getPoint();
        this.codeSmallId = boardHelp.getCodeSmall().getId();
        this.codeSmallName = boardHelp.getCodeSmall().getName();
        this.boardCreateDt = boardHelp.getCreateDt();
        this.boardCommentCnt = commentCnt;
        this.readCnt = boardHelp.getReadCnt();

        this.isSameMember = isSameMember;
        this.isAdopted = boardHelp.getIsAdopted();
        this.imagePathList.addAll(imagePathList);
    }
}
