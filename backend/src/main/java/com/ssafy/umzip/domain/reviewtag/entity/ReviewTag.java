package com.ssafy.umzip.domain.reviewtag.entity;

import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.domain.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private long id;

    @ManyToOne(cascade= CascadeType.REMOVE)
    @JoinColumn(name="tag_id")
    private Tag tag;

    @ManyToOne(cascade= CascadeType.REMOVE)
    @JoinColumn(name="review_id")
    private Review review;
}
