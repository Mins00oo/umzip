package com.ssafy.umzip.domain.tag.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagListByLargeCodeResponce {
    private long tagId;
    private int tagType;
    private String tagName;
    @Builder
    public TagListByLargeCodeResponce(long id, String name, int type){
        this.tagId=id;
        this.tagType=type;
        this.tagName=name;
    }

}
