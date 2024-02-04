package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
public class ProfileSellListDto {

    private Long boardId;
    private String title;
    private Long codeSmallId;
    private String codeSmallName;
    private Long price;
    @Setter
    private String thumbnailPath;

    @Builder
    public ProfileSellListDto(Long boardId, String title, Long codeSmallId, String codeSmallName,
                              Long price, String thumbnailPath) {
        this.boardId = boardId;
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.price = price;
        this.thumbnailPath = thumbnailPath;
    }

    public static Page<ProfileSellListDto> toDto(Page<BoardTrade> entityPage) {

        return entityPage.map(boardTrade -> ProfileSellListDto.builder()
                .boardId(boardTrade.getId())
                .title(boardTrade.getTitle())
                .codeSmallId(boardTrade.getCodeSmall().getId())
                .codeSmallName(boardTrade.getCodeSmall().getName())
                .price(boardTrade.getPrice())
                .build()
        );
    }
}
