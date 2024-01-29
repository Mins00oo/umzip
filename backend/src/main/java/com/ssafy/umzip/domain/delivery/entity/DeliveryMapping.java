package com.ssafy.umzip.domain.delivery.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_mapping_id")
    private Long id;

    @Column(name="delivery_mapping_price")
    private Long price;

    @Column(name="delivery_reissuing")
    private Long reissuing;

    @Column(name = "delivery_mapping_detail")
    private String detail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    //code_small추가 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_small_id")
    private CodeSmall codeSmall;


    @Builder
    public DeliveryMapping(Long price, Long reissuing, Delivery delivery, Member member, Company company, CodeSmall codeSmall) {
        this.price = price;
        this.reissuing = reissuing;
        this.delivery = delivery;
        this.member = member;
        this.company = company;
        this.codeSmall = codeSmall;
    }

    public void setReissuing(Long reissuing) {
        this.reissuing = reissuing;
    }

    public void setCodeSmall(CodeSmall codeSmall) {
        this.codeSmall = codeSmall;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }

}
