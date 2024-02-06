package com.ssafy.umzip.domain.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointChangeType {
    DELIVERY("용달 이용 적립"),
    CLEAN("청소 이용 적립"),
    TRADE("중고 거래 이용 적립"),
    REVIEW("후기 작성 적립"),
    HELP("도움글 채택 적립");

    private final String message;
}
