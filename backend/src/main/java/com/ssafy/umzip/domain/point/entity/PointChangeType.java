package com.ssafy.umzip.domain.point.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointChangeType {
    SAVE_BY_DELIVERY("용달 이용 적립"),
    SAVE_BY_CLEAN("청소 이용 적립"),
    SAVE_BY_TRADE_REVIEW("중고글 후기 작성 적립"),
    SAVE_BY_DELIVER_REVIEW("용달 후기 작성 적립"),
    SAVE_BY_CLEAN_REVIEW("청소 후기 작성 적립"),
    SAVE_BY_HELP("따뜻한 도움 제공"),


    USE_BY_DELIVER("용달 이용 결제 포인트 사용"),
    USE_BY_CLEAN("청소 이용 결제 포인트 사용"),
    USE_BY_HELP_ME("도와주세요 글 작성"),
    USE_BY_HELP_BY_PEOPLE("따뜻한 도움을 제공받았어요 !");

    private final String message;
}
