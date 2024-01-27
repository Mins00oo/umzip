package com.ssafy.umzip.domain.member.dto;

import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private boolean me;

    private String name;

    private String phone;

    private int point;

    private String email;

    private String avgScore;

    @Builder
    public MemberResponseDto(boolean me, String name, String phone, int point, String email, String avgScore) {
        this.me = me;
        this.name = name;
        this.phone = phone;
        this.point = point;
        this.email = email;
        this.avgScore = avgScore;
    }

    public static MemberResponseDto fromEntity(Member member, boolean isMe, String formattedAvgScore) {
        return MemberResponseDto.builder()
                .me(isMe)
                .email(member.getEmail())
                .phone(isMe ? member.getPhone() : null)
                .name(member.getName())
                .point(member.getPoint())
                .avgScore(formattedAvgScore)
                .build();
    }
}
