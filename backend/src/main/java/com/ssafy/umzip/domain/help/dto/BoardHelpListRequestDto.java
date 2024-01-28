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
@ToString
public class BoardHelpListRequestDto {

    // 카테고리
    private String codeSmallName;
    private Long codeSmallId;

    // 시군구 + 전체 + 검색
    private int sigungu;
    private String keyword;

    @Builder
    public BoardHelpListRequestDto(int sigungu, String keyword, Long codeSmallId) {
        this.sigungu = sigungu;
        this.keyword = keyword;
        setCodeSmallName(codeSmallId);
        this.codeSmallId = codeSmallId;
    }

    private void setCodeSmallName(Long codeSmallId) {
        // 잘못된 코드가 들어오는 경우는 DTO를 만들기 전에 예외처리
        if (codeSmallId == 401) { this.codeSmallName = "도와주세요"; }
        else if (codeSmallId == 402) { this.codeSmallName = "도와줄게요"; }
        else if (codeSmallId == 403) { this.codeSmallName = "도와줬어요"; }
        else if (codeSmallId == 0) { this.codeSmallName = "전체"; }
    }

    // 카테고리에서 사용
//    @Builder
//    public BoardHelpListRequestDto(int sigungu, int codeSmallId, String keyword) {
//        this.sigungu = sigungu;
//        setCodeSmallName(codeSmallId);
//        this.codeSmallId = codeSmallId;
//        this.keyword = keyword;
//    }

}
