package com.ssafy.umzip.domain.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MostTagResponseDto {
    private String tagName;

    private int tagType;

    public MostTagResponseDto(String tagName, int tagType) {
        this.tagName = tagName;
        this.tagType = tagType;
    }
}
