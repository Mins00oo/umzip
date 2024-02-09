package com.ssafy.umzip.global.util.jwt;

import com.ssafy.umzip.global.common.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberTokenDto {
    @Setter
    private int who;
    private String accessToken;
    private String refreshToken;
    @Setter
    private String name;
    @Setter
    private String profileImage;
    @Setter
    private List<Role> roleList;
    @Setter
    private Long id;

    @Builder
    public MemberTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
