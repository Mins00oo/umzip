package com.ssafy.umzip.domain.company.entity;

import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_business_authentication")
    private String businessAuthentication;

    @Column(name = "company_introduction")
    private String introduction;

    @Column(name = "company_credential")
    private String credential;

    @Column(name = "company_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "company_experience")
    private LocalDateTime experience;

    @Column(name = "company_image_url")
    private String imageUrl;

    @Column(name = "company_ceo")
    private String ceo;

    @Column(name = "company_address")
    private String address;

    @Column(name = "company_address_detail")
    private String addressDetail;

    @Column(name = "company_sigungu")
    private int sigungu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    public Company(String name, String businessAuthentication, String introduction, S3UploadDto deliverFileDto, Role role,
                   LocalDateTime experience, S3UploadDto imgDto, String ceo,
                   String address, String addressDetail, int sigungu, Member member) {
        this.name = name;
        this.businessAuthentication = businessAuthentication;
        this.introduction = introduction;
        if (deliverFileDto != null) {
            this.credential = deliverFileDto.getImgUrl();
        }
        this.role = role;
        this.experience = experience;
        if (imgDto != null) {
            this.imageUrl = imgDto.getImgUrl();
        }
        this.ceo = ceo;
        this.address = address;
        this.addressDetail = addressDetail;
        this.sigungu = sigungu;
        this.member = member;
    }
}
