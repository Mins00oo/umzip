package com.ssafy.umzip.domain.help.dto;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardHelpPostRequestDto {

    private Long codeSmallId;  // 도움주세요(401), 도움줄게요(403)
    private String title;
    private String content;
    private int point;

    @Builder
    public BoardHelpPostRequestDto(Long codeSmallId, String title, String content, int point) {
        // 사용자가 입력한 데이터
        this.codeSmallId = codeSmallId;
        this.title = title;
        this.content = content;
        this.point = point;
    }

    public BoardHelp toEntity(
            BoardHelpPostRequestDto requestDto,
            Member member,
            int sigungu,
            CodeSmall codeSmall) {
        // 도움 게시판에 저장할 데이터
        return BoardHelp.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .point(requestDto.getPoint())
                .readCnt(0)
                .sigungu(sigungu)
                .isAdopted(false)
                .codeSmall(codeSmall)
                .member(member)
                .build();
    }
}
