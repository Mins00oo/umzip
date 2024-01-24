package com.ssafy.umzip.domain.reviewreceiver.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.review.entity.Review;
import jakarta.persistence.*;

@Entity
@Table(name = "review_receiver")
public class ReviewReceiver {

    @Id
    @Column(name = "review_receiver_id")
    int id;

    @Column(name = "receiver_role")
    String receverRole;

    @ManyToOne()
    @JoinColumn(name="review_id")
    Review review;

    @ManyToOne()
    @JoinColumn(name="member_id")
    Member member;
}
