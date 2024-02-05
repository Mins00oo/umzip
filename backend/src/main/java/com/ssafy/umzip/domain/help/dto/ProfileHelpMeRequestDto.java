package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileHelpMeRequestDto {

    private Long viewMemberId;
    private boolean isSameMember;

    @Builder
    public ProfileHelpMeRequestDto(Long viewMemberId, boolean isSameMember) {
        this.viewMemberId = viewMemberId;
        this.isSameMember = isSameMember;
    }
}
