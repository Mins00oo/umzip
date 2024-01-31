package com.ssafy.umzip.domain.review.dto;

import com.ssafy.umzip.global.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyReceiveReviewResponse {
    private Long id;
    private String memberName;
    private String memberImageUrl;
    private Role receiverRole;
    private String content;
    private float score;
    private LocalDateTime createDt;
    private List<String> tag;

    public MyReceiveReviewResponse(Long id, String memberName, String memberImageUrl,
                                   Role receiverRole, String content, float score,
                                   LocalDateTime createDt) {
        this.id = id;
        this.memberName = memberName;
        this.memberImageUrl = memberImageUrl;
        this.receiverRole = receiverRole;
        this.content = content;
        this.score = score;
        this.createDt = createDt;
    }
}
