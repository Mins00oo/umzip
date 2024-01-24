package com.ssafy.umzip.global.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    // Success
    SUCCESS(true, 100, "요청에 성공하였습니다."),

    // COMMON
    S3_UPLOAD_FAIL(false, 200, "사진 업로드에 실패하였습니다."),
    TRANSLATE_FILE_FAILED(false, 201, "파일 변환에 실패하였습니다"),

    // MEMBER
    ALREADY_EXIST_MEMBER(false, 300, "이미 존재하는 계정입니다."),
    NOT_VALID_PASSWORD(false, 301, "비밀번호 입력값이 잘못되었습니다."),
    NOT_VALID_EMAIL(false, 302, "해당 이메일의 계정을 찾을 수 없습니다."),
    ALREADY_EXIST_PHONE_NUMBER(false, 303, "이미 가입된 전화번호입니다."),
    NOT_VALID_AUTH_CODE(false, 304, "인증번호가 잘못 되었습니다."),


    // BoardHelp
    CODE_DOES_NOT_EXIST(false, 500, "해당 코드 소분류가 존재하지 않습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    StatusCode(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
