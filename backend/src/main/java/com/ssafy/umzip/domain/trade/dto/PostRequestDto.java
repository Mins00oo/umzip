package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private Long price;
    private String content;
    private boolean isDirect;
    private int sigungu;
    private String sigunguName;
    private String address;

    @Builder
    public PostRequestDto(String title, Long price, String content,
                          boolean isDirect, int sigungu, String sigunguName,
                          String address) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.isDirect = isDirect;
        this.sigungu = sigungu;
        this.sigunguName = sigunguName;
        this.address = address;
    }

    public BoardTrade toEntity(PostRequestDto requestDto,
                               Member member, CodeSmall codeSmall,
                               int readCnt) {
        return BoardTrade.builder()
                .member(member)
                .codeSmall(codeSmall)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .price(requestDto.getPrice())
                .isDirect(requestDto.isDirect())
                .address(requestDto.getAddress())
                .readCnt(readCnt)
                .sigungu(requestDto.getSigungu())
                .sigunguName(requestDto.getSigunguName())
                .build();
    }
}
