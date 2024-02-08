package com.ssafy.umzip.domain.trade.dto;

import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class DetailDto {

    private Long boardId;
    private List<String> filePathList;
    private Long writerId;
    private String writerName;
    private String writerImageUrl;
    private Double writerRating;
    private String writerAddress;
    private String title;
    private Long price;
    private LocalDateTime createDt;
    private boolean isDirect;
    private String content;
    private int readCnt;
    private Long codeSmallId;
    private String codeSmallName;
    private boolean isWriter;
    private boolean isActive;

    @Builder
    public DetailDto(Long boardId, List<String> filePathList,
                     Long writerId, String writerName, String writerImageUrl, Double writerRating, String writerAddress,
                     String title, Long price, LocalDateTime createDt, boolean isDirect,
                     String content, int readCnt, Long codeSmallId, String codeSmallName,
                     boolean isWriter, boolean isActive) {
        this.boardId = boardId;
        this.filePathList = filePathList;
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerImageUrl = writerImageUrl;
        this.writerRating = writerRating;
        this.writerAddress = writerAddress;
        this.title = title;
        this.price = price;
        this.createDt = createDt;
        this.isDirect = isDirect;
        this.content = content;
        this.readCnt = readCnt;

        if (isActive) {
            this.codeSmallName = "구매완료";
            this.codeSmallId = 303L;
        } else {
            this.codeSmallName = codeSmallName;
            this.codeSmallId = codeSmallId;
        }

        this.isWriter = isWriter;
        this.isActive = isActive;
    }

    public static DetailDto toDto(BoardTrade boardTrade, Double rating, List<String> filePathList,
                                  boolean isWriter, boolean isActive) {
        return DetailDto.builder()
                .boardId(boardTrade.getId())
                .filePathList(filePathList)
                .writerId(boardTrade.getMember().getId())
                .writerName(boardTrade.getMember().getName())
                .writerImageUrl(boardTrade.getMember().getImageUrl())
                .writerRating(rating)
                .writerAddress(boardTrade.getMember().getAddress())
                .title(boardTrade.getTitle())
                .price(boardTrade.getPrice())
                .createDt(boardTrade.getCreateDt())
                .isDirect(boardTrade.getIsDirect())
                .content(boardTrade.getContent())
                .readCnt(boardTrade.getReadCnt())
                .codeSmallId(boardTrade.getCodeSmall().getId())
                .codeSmallName(boardTrade.getCodeSmall().getName())
                .isWriter(isWriter)
                .isActive(isActive)
                .build();
    }
}
