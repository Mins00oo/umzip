package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileListDto {

    private Long boardId;
    private String title;
    private Long codeSmallId;
    private String codeSmallName;
    private Long price;
    private String thumbnailPath;

    @Builder
    public ProfileListDto(Long boardId, String title, Long codeSmallId, String codeSmallName,
                          Long price, String thumbnailPath) {
        this.boardId = boardId;
        this.title = title;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.price = price;
        this.thumbnailPath = thumbnailPath;
    }
}
