package com.ssafy.umzip.domain.tag.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "code_large_id")
    private Long codeLargeId;

    @Column(name = "tag_name")
    private String tagName;

    public Tag() {
    }

    public Tag(Long codeLargeId, String tagName) {
        this.codeLargeId = codeLargeId;
        this.tagName = tagName;
    }
}