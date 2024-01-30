package com.ssafy.umzip.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllTagResponse {
    private String userName;
    private String imagePath;
    private Float score;
    private List<String> tagName;
    private String content;
    private LocalDateTime createTime;
}
