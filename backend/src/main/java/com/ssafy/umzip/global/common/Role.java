package com.ssafy.umzip.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("사용자"),
    CLEAN("청소업체"),
    DELIVER("용달업체");

    private final String role;
}