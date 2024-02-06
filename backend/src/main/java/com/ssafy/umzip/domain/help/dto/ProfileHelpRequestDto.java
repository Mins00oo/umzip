package com.ssafy.umzip.domain.help.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileHelpRequestDto {

    private Long viewMemberId;
    private boolean isSameMember;

    @Builder
    public ProfileHelpRequestDto(Long viewMemberId, boolean isSameMember) {
        this.viewMemberId = viewMemberId;
        this.isSameMember = isSameMember;
    }
}
