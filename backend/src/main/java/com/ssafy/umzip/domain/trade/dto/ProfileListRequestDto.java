package com.ssafy.umzip.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileListRequestDto {

    private Long viewMemberId;  // 중고글을 보고 싶은 member( 나 또는 너 )
    private boolean isSameMember;   // 같은지 여부

    @Builder
    public ProfileListRequestDto(Long viewMemberId, boolean isSameMember) {
        this.viewMemberId = viewMemberId;
        this.isSameMember = isSameMember;
    }
}
