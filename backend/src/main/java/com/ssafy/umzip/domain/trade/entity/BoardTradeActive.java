package com.ssafy.umzip.domain.trade.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="board_trade_active")
public class BoardTradeActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_trade_active_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_trade_id")
    private BoardTrade boardTrade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "board_trade_is_active")
    private Boolean isActive;

    @Builder
    public BoardTradeActive(Long id, BoardTrade boardTrade, Member member,
                            Boolean isActive) {
        this.id = id;
        this.boardTrade = boardTrade;
        this.member = member;
        this.isActive = isActive;
    }
}
