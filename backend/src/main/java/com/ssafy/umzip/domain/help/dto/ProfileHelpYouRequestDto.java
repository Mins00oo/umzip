package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileHelpYouRequestDto {

    private Long viewMemberId;
    private boolean isSameMember;

    @Builder
    public ProfileHelpYouRequestDto(Long viewMemberId, boolean isSameMember) {
        this.viewMemberId = viewMemberId;
        this.isSameMember = isSameMember;
    }
}
