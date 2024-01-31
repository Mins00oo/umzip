package com.ssafy.umzip.domain.review.entity;


import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;

    @Column(name = "review_score")
    private float score;

    @Column(name = "review_content")
    private String content;

    @ManyToOne(cascade= CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Review(float score, String content, Member member) {
        this.score = score;
        this.content = content;
        this.member = member;
    }
}
