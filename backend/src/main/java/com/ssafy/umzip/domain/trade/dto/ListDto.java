package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

@Getter
public class ListDto {

    Long boardId;
    @Setter
    String thumbnailPath;
    String title;
    String address;
    Long price;
    int readCnt;
    Long codeSmallId;
    String codeSmallName;

    @Builder
    public ListDto(Long boardId, String thumbnailPath, String title,
                   String address, Long price, int readCnt, Long codeSmallId) {
        this.boardId = boardId;
        this.thumbnailPath = thumbnailPath;
        this.title = title;
        this.address = address;
        this.price = price;
        this.readCnt = readCnt;
        this.codeSmallId = codeSmallId;
        setCodeSmallName(codeSmallId);
    }

    public static Slice<ListDto> toDto(Slice<BoardTrade> boardTradeSlice) {
        return boardTradeSlice.map(boardTrade -> ListDto.builder()
                .boardId(boardTrade.getId())
                .title(boardTrade.getTitle())
                .address(boardTrade.getAddress())
                .price(boardTrade.getPrice())
                .readCnt(boardTrade.getReadCnt())
                .codeSmallId(boardTrade.getCodeSmall().getId())
                .build());
    }
    
    private void setCodeSmallName(Long codeSmallId) {
        if (codeSmallId == 301) { this.codeSmallName = "판매중"; }
        else if (codeSmallId == 302) { this.codeSmallName = "판매완료"; }
        else if (codeSmallId == 303) { this.codeSmallName = "구매완료"; }
    }

}
