package com.ssafy.umzip.domain.trade.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="board_trade_image")
public class BoardTradeImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_trade_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_trade_id")
    private BoardTrade boardTrade;

    @Column(name="board_trade_image_origin_name")
    private String originName;

    @Column(name="board_trade_image_saved_name")
    private String savedName;

    @Column(name="board_trade_image_path")
    private String path;

    @Builder
    public BoardTradeImage(S3UploadDto s3UploadDto, BoardTrade boardTrade) {

        this.originName = s3UploadDto.getOriginName();
        this.savedName = s3UploadDto.getSaveName();
        this.path = s3UploadDto.getImgUrl();

        this.boardTrade = boardTrade;
    }
}
