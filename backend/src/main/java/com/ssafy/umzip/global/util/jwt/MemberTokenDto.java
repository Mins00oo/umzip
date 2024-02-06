package com.ssafy.umzip.global.util.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberTokenDto {
    private String accessToken;
    private String refreshToken;
    @Setter
    private String name;
    @Setter
    private String profileImage;

    @Builder
    public MemberTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
