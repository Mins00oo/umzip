package com.ssafy.umzip.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberLoginRequestDto {
    private String email;

    private String pwd;

    public MemberLoginRequestDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
