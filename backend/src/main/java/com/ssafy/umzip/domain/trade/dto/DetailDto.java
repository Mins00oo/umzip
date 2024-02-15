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
    private boolean isWriter;
    private Long codeSmallId;

    @Builder
    public DetailDto(Long boardId, List<String> filePathList,
                     Long writerId, String writerName, String writerImageUrl, Double writerRating, String writerAddress,
                     String title, Long price, LocalDateTime createDt, boolean isDirect,
                     String content, int readCnt, boolean isWriter, Long codeSmallId) {
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

        this.isWriter = isWriter;
        this.codeSmallId = codeSmallId;
    }

    public static DetailDto toDto(BoardTrade boardTrade, Double rating, List<String> filePathList,
                                  boolean isWriter) {
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
                .isWriter(isWriter)
                .codeSmallId(boardTrade.getCodeSmall().getId())
                .build();
    }
}
