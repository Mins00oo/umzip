package com.ssafy.umzip.domain.reviewreceiver.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.review.entity.Review;
import com.ssafy.umzip.global.common.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "review_receiver")
@Getter
public class ReviewReceiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_receiver_id")
    int id;

    @Column(name = "receiver_role")
    @Enumerated(EnumType.STRING)
    private Role receiverRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    Member member;
}
