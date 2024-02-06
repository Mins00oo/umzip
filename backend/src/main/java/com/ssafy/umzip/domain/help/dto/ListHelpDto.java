package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@ToString
@Getter
public class ListHelpDto {

    private Long id;
    private String writerName;
    private String title;
    private String codeSmallName;
    private Long codeSmallId;
    private LocalDateTime createDt;
    @Setter
    private Long commentCnt;
    private int readCnt;
    private int rewardPoint;
    private int sigungu;
    private boolean isAdopted;



    @Builder
    public ListHelpDto(Long id, String writerName, String title,
                       Long codeSmallId, LocalDateTime createDt,
                       int readCnt, int rewardPoint, int sigungu,
                       boolean isAdopted) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        setCodeSmallName(codeSmallId);
        this.codeSmallId = codeSmallId;
        this.createDt = createDt;
        this.readCnt = readCnt;
        this.rewardPoint = rewardPoint;
        this.sigungu = sigungu;
        this.isAdopted = isAdopted;
    }

    public static Page<ListHelpDto> toDto(Page<BoardHelp> boardHelpPage) {
        return  boardHelpPage.map(boardHelp -> ListHelpDto.builder()
                .id(boardHelp.getId())
                .writerName(boardHelp.getMember().getName())
                .title(boardHelp.getTitle())
                .codeSmallId(boardHelp.getCodeSmall().getId())
                .createDt(boardHelp.getCreateDt())
                .readCnt(boardHelp.getReadCnt())
                .rewardPoint(boardHelp.getPoint())
                .sigungu(boardHelp.getSigungu())
                .isAdopted(boardHelp.getIsAdopted())
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
