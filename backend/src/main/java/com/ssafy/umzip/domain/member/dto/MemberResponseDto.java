package com.ssafy.umzip.domain.member.dto;

import com.ssafy.umzip.domain.company.dto.MostTagResponseDto;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private boolean me;

    private String name;

    private String phone;

    private int point;

    private String email;

    private String avgScore;

    private List<MostTagResponseDto> tagList;

    private String imageUrl;

    @Builder
    public MemberResponseDto(boolean me, String name, String phone, int point, String email, String avgScore,
                             List<MostTagResponseDto> tagList, String imageUrl) {
        this.me = me;
        this.name = name;
        this.phone = phone;
        this.point = point;
        this.email = email;
        this.avgScore = avgScore;
        this.tagList = tagList;
        this.imageUrl = imageUrl;
    }


    public static MemberResponseDto fromEntity(Member member, boolean isMe, String formattedAvgScore,
                                               List<MostTagResponseDto> tagList) {
        return MemberResponseDto.builder()
                .me(isMe)
                .email(member.getEmail())
                .phone(isMe ? member.getPhone() : null)
                .name(member.getName())
                .point(member.getPoint())
                .avgScore(formattedAvgScore)
                .tagList(tagList)
                .imageUrl(member.getImageUrl())
                .build();
    }
}
