package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
public class ProfileBuyListDto {

    private Long boardId;
    private String title;
    private Long codeSmallId;
    private String codeSmallName;
    private Long price;
    @Setter
    private String thumbnailPath;

    @Builder
    public ProfileBuyListDto(Long boardId, String title, Long codeSmallId, String codeSmallName,
                              Long price, String thumbnailPath) {
        this.boardId = boardId;
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.price = price;
        this.thumbnailPath = thumbnailPath;
    }

    public static Page<ProfileBuyListDto> toDto(Page<BoardTrade> entityPage) {

        return entityPage.map(boardTrade -> ProfileBuyListDto.builder()
                .boardId(boardTrade.getId())
                .title(boardTrade.getTitle())
                .codeSmallId(303L)
                .codeSmallName("구매완료")
                .price(boardTrade.getPrice())
                .build()
        );
    }
}
