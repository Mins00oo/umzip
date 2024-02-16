package com.ssafy.umzip.domain.review.dto;

import com.ssafy.umzip.global.common.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MyReceiveReviewResponse {
    private Long id;
    private Long memberId;
    private String memberName;
    private String memberImageUrl;
    private Role receiverRole;
    private String content;
    private float score;
    private LocalDateTime createDt;
    private List<String> tag = new ArrayList<>();
    private List<Integer> tagType = new ArrayList<>();

    public MyReceiveReviewResponse(Long id, Long memberId, String memberName, String memberImageUrl,
                                   Role receiverRole, String content, float score,
                                   LocalDateTime createDt) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImageUrl = memberImageUrl;
        this.receiverRole = receiverRole;
        this.content = content;
        this.score = score;
        this.createDt = createDt;
    }
    public MyReceiveReviewResponse(Long id, Long memberId, String memberName, String memberImageUrl,
                                   Role receiverRole, String content, float score,
                                   LocalDateTime createDt, String tag) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImageUrl = memberImageUrl;
        this.receiverRole = receiverRole;
        this.content = content;
        this.score = score;
        this.createDt = createDt;
        this.tag.add(tag);
    }
}
