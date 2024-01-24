package com.ssafy.umzip.domain.code.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "code_large")
public class CodeLarge  {
    @Id
    @Column(name = "code_large_id")
    private Long id;

    @Column(name = "code_large_name")
    private String name;
}

