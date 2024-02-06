package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardTradeListDto {

    private Long boardId;
    private String title;
    private String address;
    private Long price;
    private int readCnt;
    private Long codeSmallId;
    private String codeSmallName;
    private String thumbnailPath;

    @Builder
    public BoardTradeListDto(BoardTrade boardTrade, String thumbnailPath) {
        this.boardId = boardTrade.getId();
        this.title = boardTrade.getTitle();
        this.address = boardTrade.getAddress();
        this.price = boardTrade.getPrice();
        this.readCnt = boardTrade.getReadCnt();
        this.codeSmallId = boardTrade.getCodeSmall().getId();
        this.codeSmallName = boardTrade.getCodeSmall().getName();
        this.thumbnailPath = thumbnailPath;
    }

    public BoardTradeListDto(Long boardId, String title, String address,
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
