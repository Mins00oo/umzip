package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class BoardHelpListDto {

    private Long id;
    private String writerName;
    private String title;
    private String codeSmallName;
    private Long codeSmallId;
    private LocalDateTime createDt;
    private int commentCnt;             // comment table
    private int readCnt;
    private int rewardPoint;



    @Builder
    public BoardHelpListDto(Long id, String writerName, String title,
                            Long codeSmallId, LocalDateTime createDt,
                            int commentCnt, int readCnt, int rewardPoint) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        setCodeSmallName(codeSmallId);
        this.codeSmallId = codeSmallId;
        this.createDt = createDt;
        this.commentCnt = commentCnt;
        this.readCnt = readCnt;
        this.rewardPoint = rewardPoint;
    }

    public static Page<BoardHelpListDto> toDto(Page<BoardHelp> boardHelpPage) {

        return boardHelpPage.map(m -> BoardHelpListDto.builder()
                .id(m.getId())
                .writerName(m.getMember().getName())
                .title(m.getTitle())
                .codeSmallId(m.getCodeSmall().getId())
                .createDt(m.getCreateDt())  // commentCnt를 위한 Comment Entity 필요
                .readCnt(m.getReadCnt())
                .rewardPoint(m.getPoint())
                .build());
    }

    private void setCodeSmallName(Long codeSmallId) {
        // 잘못된 코드가 들어오는 경우는 DTO를 만들기 전에 예외처리
        if (codeSmallId == 401) { this.codeSmallName = "도와주세요"; }
        else if (codeSmallId == 402) { this.codeSmallName = "도와줄게요"; }
        else if (codeSmallId == 403) { this.codeSmallName = "도와줬어요"; }
        else if (codeSmallId == 0) { this.codeSmallName = "전체"; }
    }
}
