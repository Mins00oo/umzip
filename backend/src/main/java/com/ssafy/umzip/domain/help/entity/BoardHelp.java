package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_help")
public class BoardHelp extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_help_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_small_id")
    private CodeSmall codeSmall;

    @Column(name="board_help_title")
    private String title;

    @Column(name="board_help_content")
    private String content;

    @Column(name="board_help_point")
    private int point;

    @Setter
    @Column(name="board_help_read_count")
    private int readCnt;

    @Column(name="board_help_sigungu")
    private int sigungu;

    @Setter
    @Column(name="board_help_adopted")
    private Boolean isAdopted;

    @Builder
    public BoardHelp(String title, String content, int point, int readCnt,
                     int sigungu, Boolean isAdopted,
                     CodeSmall codeSmall, Member member) {
        this.title = title;
        this.content = content;
        this.point = point;
        this.readCnt = readCnt;
        this.sigungu = sigungu;
        this.isAdopted = isAdopted;
        this.codeSmall = codeSmall;
        this.member = member;
    }
}
