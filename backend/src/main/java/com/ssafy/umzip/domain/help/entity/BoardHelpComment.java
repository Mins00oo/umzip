package com.ssafy.umzip.domain.help.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="board_help_comment")
public class BoardHelpComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_help_comment_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name="board_help_id")
//    private BoardHelp boardHelp;
//
//    @ManyToOne
//    @JoinColumn(name="member_id")
//    private Member member;

    @Column(name="board_help_comment_content")
    private String content;

    @Builder
    public BoardHelpComment(String content) {
        this.content = content;
    }
}
