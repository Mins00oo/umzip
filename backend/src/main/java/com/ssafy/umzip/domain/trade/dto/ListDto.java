package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.*;

@Getter
public class ListDto {

    private Long boardId;
    private String title;
    private String address;
    private Long price;
    private int readCnt;
    private Long codeSmallId;
    private String codeSmallName;
    private String thumbnailPath;

    @Builder
    public ListDto(Long boardId, String title, String address,
                   Long price, int readCnt, Long codeSmallId,
                   String codeSmallName, String thumbnailPath) {
        this.boardId = boardId;
        this.title = title;
        this.address = address;
        this.price = price;
        this.readCnt = readCnt;
        this.codeSmallId = codeSmallId;
        this.codeSmallName = codeSmallName;
        this.thumbnailPath = thumbnailPath;
    }
}
