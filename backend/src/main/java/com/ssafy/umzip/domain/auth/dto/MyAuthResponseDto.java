package com.ssafy.umzip.domain.auth.dto;

import com.ssafy.umzip.domain.chat.dto.ChatMessageRequestDto;
import com.ssafy.umzip.domain.chat.entity.ChatMessage;
import com.ssafy.umzip.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyAuthResponseDto {
    private String name;

    private String profileImage;

    @Builder
    public MyAuthResponseDto(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public static MyAuthResponseDto fromEntity(
            Member member
    ) {
        return MyAuthResponseDto.builder()
                .name(member.getName())
                .profileImage(member.getImageUrl())
                .build();
    }


}
