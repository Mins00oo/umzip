package com.ssafy.umzip.domain.review.entity;


import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.reviewtag.entity.ReviewTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;

    @Column(name = "review_score")
    private float score;

    @Column(name = "review_content")
    private String content;

    @Column(name = "review_create_dt")
    private LocalDateTime createDt;

    @Column(name = "review_update_dt")
    private LocalDateTime updateDt;

    @ManyToOne(cascade= CascadeType.REMOVE)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<ReviewTag> reviewTags;

    @Builder
    public Review(float score, String content, LocalDateTime time, Boolean isUpdate) {
        this.score = score;
        this.content = content;
        if (isUpdate) {
            this.updateDt = time;
        } else {
            this.createDt = time;
        }
    }
    public static Review create(float score, String content, LocalDateTime createDt) {
        return Review.builder()
                .score(score)
                .content(content)
                .time(createDt)
                .build();
    }
    public static Review update(float score, String content, LocalDateTime updateDt) {
        return Review.builder()
                .score(score)
                .content(content)
                .time(updateDt)
                .build();
    }
}
