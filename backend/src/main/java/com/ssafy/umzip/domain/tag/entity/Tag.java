package com.ssafy.umzip.domain.tag.entity;

import com.ssafy.umzip.domain.code.entity.CodeLarge;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne(cascade= CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name="code_large_id")
    private CodeLarge codeLarge;

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }
    public Tag(){}
}