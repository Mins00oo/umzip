package com.ssafy.umzip.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private String email;

    private String pwd;
}
