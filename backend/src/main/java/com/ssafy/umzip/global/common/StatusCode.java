package com.ssafy.umzip.global.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    // Success
    SUCCESS(true, 100, "요청에 성공하였습니다."),

    // COMMON
    S3_UPLOAD_FAIL(false, 200, "사진 업로드에 실패하였습니다."),
    TRANSLATE_FILE_FAILED(false, 201, "파일 변환에 실패하였습니다"),
    FORBIDDEN_REQUEST(false, 202, "접근 권한이 없습니다."),

    // MEMBER : 300
    ALREADY_EXIST_MEMBER(false, 300, "이미 존재하는 계정입니다."),
    NOT_VALID_PASSWORD(false, 301, "비밀번호 입력값이 잘못되었습니다."),
    NOT_VALID_EMAIL(false, 302, "해당 이메일의 계정을 찾을 수 없습니다."),
    ALREADY_EXIST_PHONE_NUMBER(false, 303, "이미 가입된 전화번호입니다."),
    NOT_VALID_AUTH_CODE(false, 304, "인증번호가 잘못 되었습니다."),
    ALREADY_EXIST_EMAIL(false, 305, "중복된 이메일입니다."),
    COMPANY_ROLE_NOT_MATCH(false, 306, "일치하는 업체의 권한을 찾을 수 없습니다."),
    LOGIN_FAILED(false, 307, "로그인에 실패하였습니다."),
    INVALID_TOKEN(false, 308, "토큰이 유효하지 않습니다."),
    INVALID_NULL_TOKEN(false, 308, "토큰 값 자체가 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(false, 309, "토큰이 만료되었습니다."),
    DAMAGED_ACCESS_TOKEN(false, 310, "손상된 토큰입니다."),
    UNSUPPORTED_ACCESS_TOKEN(false, 311, "지원하지 않는 토큰입니다."),
    NOT_EXIST_MEMBER(false,312,"존재하지 않는 회원입니다."),
    NOT_VALID_MEMBER_PK(false, 313, "해당 ID로 계정을 찾을 수 없습니다."),

    // COMPANY : 400
    NOT_EXIST_COMPANY(false,400,"존재하지 않는 회사입니다."),
    NOT_VALID_BUSINESS_NUMBER(false, 401, "등록되지 않은 사업자 번호입니다."),

    // BoardHelp: 500
    CODE_DOES_NOT_EXIST(false, 500, "해당 코드 소분류가 존재하지 않습니다."),
    NOT_EXIST_CODE(false, 501, "해당 코드 소분류가 존재하지 않습니다."),
    NOT_EXIST_BOARD(false, 502, "존재하지 않는 게시글 입니다."),
    NOT_VALID_BOARD_PK(false, 503, "해당 ID로 게시글을 찾을 수 없습니다."),
    NOT_POST_COMMENT(false, 504, "채택된 글에 댓글을 작성할 수 없습니다."),
    NOT_EXIST_COMMENT_PK(false, 505, "해당 ID로 댓글을 찾을 수 없습니다."),
    ALREADY_ADOPT_BOARD(false, 506, "이미 채택된 글입니다."),

    // DELIVERY : 600
    NOT_EXIST_CAR(false,600, "존재하지 않는 차종입니다."),
    NOT_EXIST_MAPPING(false,601,"존재하지 않는 용달 매칭건입니다."),
    NOT_EXIST_FUEL(false,602,"일치하는 연료의 종류가 없습니다."),
    FAIL_API_REQUEST(false,603,"외부 API 요청의 반환값이 없습니다."),
    NOT_EXIST_DELIVERY(false,604,"존재하지 않는 용달 주문건입니다."),
    FAIL_TO_QUOTATION(false,605,"견적 제안 요청이 실패했습니다."),
    INVALID_ACCESS_DELIVERY(false,606,"요청한 업체와 수정 적용 대상이 다른 업체입니다"),
    FAIL_TO_REJECTION(false,607,"업체의 견적 거절 요청이 실패했습니다."),
    


    // REVIEW & TAG : 700
    NOT_EXIST_REVIEW(false,700, "존재하지 않는 리뷰입니다."),
    NOT_EXIST_TAG(false,701, "존재하지 않는 태그입니다."),

    // CLEAN : 800
    NOT_EXIST_CLEAN_MAPPING(false,801,"존재하지 않는 청소 매칭건입니다."),
    NOT_EXIST_CLEAN(false,802,"존재하지 않는 청소 주문건입니다."),
    INVALID_ACCESS_CLEAN(false,803,"요청한 업체와 수정 적용 대상이 다른 업체입니다"),
    INVALID_ACCESS_CLEAN_MAPPING(false,804,"요청한 유저와 수정 적용 대상이 다른 유저입니다.");





    private final boolean isSuccess;
    private final int code;
    private final String message;

    StatusCode(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
