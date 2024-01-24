package com.ssafy.umzip.domain.tag.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagListByLargeCodeResponce {
    private long tagId;
    private String tagName;
}
