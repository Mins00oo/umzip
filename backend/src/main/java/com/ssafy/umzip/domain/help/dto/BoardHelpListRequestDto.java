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
//    private String codeSmallName;
//    private int codeSmallId;

    // 시군구 + 전체 + 검색
    private int sigungu;
    private String keyword;

    @Builder
    public BoardHelpListRequestDto(int sigungu, String keyword) {
        this.sigungu = sigungu;
        this.keyword = keyword;
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
