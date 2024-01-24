package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class BoardHelpListRequestDto {

    private Long id;

//    private Member memberId;

    private Long codeSmallId;
    private String title;
    private String content;
    private int point;
    private int readCnt;
    private int sigungu;
    private Boolean isAdopted;

    // codeSmall도 초기화 해야 함
    public static BoardHelp toEntity(BoardHelpListRequestDto requestDto, int codeSmall, int curMemberSigungu) {
        return BoardHelp.builder()

                .title(requestDto.title)
                .content(requestDto.content)
                .point(requestDto.point)
                .readCnt(requestDto.readCnt)
                .isAdopted(requestDto.isAdopted)
                .sigungu(curMemberSigungu)
                .build();
    }

}
