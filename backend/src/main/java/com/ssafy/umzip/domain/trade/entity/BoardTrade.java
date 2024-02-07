package com.ssafy.umzip.domain.trade.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="board_trade")
public class BoardTrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_trade_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_small_id")
    @Setter
    private CodeSmall codeSmall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "boardTrade", fetch = FetchType.LAZY)
    private List<BoardTradeImage> images = new ArrayList<>();

    @Column(name="board_trade_title")
    private String title;

    @Column(name="board_trade_content")
    private String content;

    // 모든 price의 자료형을 Long으로 통일함
    @Column(name="board_trade_price")
    private Long price;

    @Column(name="board_trade_is_direct")
    private Boolean isDirect;

    // address: 기본 주소 - 경기 성남시 분당구 판교역로 166
    @Column(name="board_trade_address")
    private String address;

    @Setter
    @Column(name="board_trade_read_count")
    private int readCnt;

    @Column(name="board_trade_sigungu")
    private int sigungu;

    @Column(name="board_trade_sigungu_name")
    private String sigunguName;

    @Builder
    public BoardTrade(Long id, CodeSmall codeSmall, Member member,
                      String title, String content, Long price,
                      Boolean isDirect, String address, int readCnt,
                      int sigungu, String sigunguName) {
        this.id = id;
        this.codeSmall = codeSmall;
        this.member = member;
        this.title = title;
        this.content = content;
        this.price = price;
        this.isDirect = isDirect;
        this.address = address;
        this.readCnt = readCnt;
        this.sigungu = sigungu;
        this.sigunguName = sigunguName;
    }
}
