package com.ssafy.umzip.domain.member.entity;

import com.ssafy.umzip.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_phone")
    private String phone;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_point")
    private int point;

    @Column(name = "member_image_url")
    private String imageUrl;

    @Column(name = "member_address")
    private String address;

    @Column(name = "member_address_detail")
    private String addressDetail;

    @Column(name = "member_sigungu")
    private int sigungu;

    @Column(name = "member_pwd")
    private String pwd;

    @Builder
    public Member(String email, String phone, String name, int point, String address, String addressDetail, int sigungu, String pwd) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.point = point;
        this.address = address;
        this.addressDetail = addressDetail;
        this.sigungu = sigungu;
        this.pwd = pwd;
    }

}
