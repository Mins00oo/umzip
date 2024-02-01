package com.ssafy.umzip.domain.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatRoomStatus {

    ENTER("입장"),
    TALK("대화중"),
    LEAVE("떠남");

    private final String status;
}
