package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostRequestDto {

    private String title;
    private Long price;
    private String content;
    private Boolean isDirect;
    private int sigungu;
    private String sigunguName;
    private String address;

    @Builder
    public PostRequestDto(String title, Long price, String content,
                          Boolean isDirect, int sigungu, String sigunguName,
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
                .isDirect(requestDto.getIsDirect())
                .address(requestDto.getAddress())
                .readCnt(readCnt)
                .sigungu(requestDto.getSigungu())
                .sigunguName(requestDto.getSigunguName())
                .build();
    }

    @Override
    public String toString() {
        return "PostRequestDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", isDirect=" + isDirect +
                ", sigungu='" + sigungu + '\'' +
                ", sigunguName='" + sigunguName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
