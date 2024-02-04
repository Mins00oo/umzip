package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ProfileListDto {

    private Long boardId;
    private String title;
    private Long codeSmallId;
    private Long price;
    @Setter
    private String thumbnailPath;

    @Builder
    public ProfileListDto(Long boardId, String title, Long codeSmallId,
                          Long price, String thumbnailPath) {
        this.boardId = boardId;
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.price = price;
        this.thumbnailPath = thumbnailPath;
    }

    public static Page<ProfileListDto> toDto(Page<BoardTrade> entityPage) {

        return entityPage.map(boardTrade -> ProfileListDto.builder()
                .boardId(boardTrade.getId())
                .title(boardTrade.getTitle())
                .codeSmallId(boardTrade.getCodeSmall().getId())
                .price(boardTrade.getPrice())
                .build()
        );
    }
}
