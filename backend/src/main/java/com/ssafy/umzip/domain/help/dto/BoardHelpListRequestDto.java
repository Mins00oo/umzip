package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class BoardHelpListRequestDto {

    private String codeSmallName;
    private Long codeSmallId;
    private int sigungu;
    private String keyword;
    private Long memberId;

    @Builder
    public BoardHelpListRequestDto(int sigungu, String keyword, Long codeSmallId, Long memberId) {
        this.sigungu = sigungu;
        this.keyword = keyword;
        setCodeSmallName(codeSmallId);
        this.codeSmallId = codeSmallId;
        this.memberId = memberId;
    }

    private void setCodeSmallName(Long codeSmallId) {
        // 잘못된 코드가 들어오는 경우는 DTO를 만들기 전에 예외처리
        if (codeSmallId == 401) { this.codeSmallName = "도와주세요"; }
        else if (codeSmallId == 402) { this.codeSmallName = "도와줄게요"; }
        else if (codeSmallId == 403) { this.codeSmallName = "도와줬어요"; }
        else if (codeSmallId == 0) { this.codeSmallName = "전체"; }
    }
}
