package com.ssafy.umzip.domain.code.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "code_small")
@Getter
public class CodeSmall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_small_id")
    private Long id;

    @Column(name = "code_small_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_large_id")
    CodeLarge codeLarge;

}
