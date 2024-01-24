package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;

import java.time.LocalDateTime;

public class BoardHelpListDto {

    private Long id;
    private String writerName;
    private String title;
    private String typeName;    // 도와줘요, 도와줬어요, 도와줄게요
    private int typeNumber;
    private int commentCnt;
    private int readCnt;
    private int rewardPoint;
    private LocalDateTime createDt;

    public void setTypeName(int typeNumber) {
        if (typeNumber == 401) {
            this.typeName = "도와주세요";
        }
        else if (typeNumber == 402) {
            this.typeName = "도와줄게요";
        }
        else {
            this.typeName = "도와줬어요";
        }
    }

    public BoardHelpListDto(BoardHelp boardHelp, String writerName,
                            String typeName, int commentCnt) {
        this.id = boardHelp.getId();
        this.writerName = writerName;
        this.title = boardHelp.getTitle();
        this.typeName = typeName;
        // setTypeName(boardHelp.);
        this.commentCnt = commentCnt;
        this.readCnt = boardHelp.getReadCnt();
        this.rewardPoint = boardHelp.getPoint();
        this.createDt = boardHelp.getCreateDt();
    }


}
