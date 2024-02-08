package com.ssafy.umzip.domain.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointChangeType {
    SAVE_BY_DELIVERY("용달 이용 적립"),
    SAVE_BY_CLEAN("청소 이용 적립"),
    SAVE_BY_TRADE_REVIEW("중고글 후기 작성 적립"),
    SAVE_BY_HELP("따뜻한 도움 제공");

    private final String message;
}
