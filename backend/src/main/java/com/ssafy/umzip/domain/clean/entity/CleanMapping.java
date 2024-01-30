package com.ssafy.umzip.domain.clean.entity;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.company.entity.Company;
import com.ssafy.umzip.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CleanMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clean_mapping_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clean_id")
    private Clean clean;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_small_id")
    private CodeSmall codeSmall;
    @Column(name = "clean_mapping_price")
    private Long price;
    @Column(name = "clean_mapping_reissuing")
    private Long reissuing;
    @Column(name = "clean_mapping_detail")
    private String detail;

}
