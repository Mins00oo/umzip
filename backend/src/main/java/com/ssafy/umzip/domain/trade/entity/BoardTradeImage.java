package com.ssafy.umzip.domain.trade.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    public BoardTradeImage(Long id, BoardTrade boardTrade,
                           String originName, String savedName, String path) {
        this.id = id;
        this.boardTrade = boardTrade;
        this.originName = originName;
        this.savedName = savedName;
        this.path = path;
    }
}
