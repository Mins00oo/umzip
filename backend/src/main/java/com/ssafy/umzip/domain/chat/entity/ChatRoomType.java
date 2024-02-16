package com.ssafy.umzip.domain.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatRoomType {

    TRADE("입장"),
    HELP("도움"),
    DELIVER("용달"),
    CLEAN("청소");

    private final String type;
}
