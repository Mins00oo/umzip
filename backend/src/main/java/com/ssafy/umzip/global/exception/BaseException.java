package com.ssafy.umzip.global.exception;

import com.ssafy.umzip.global.common.StatusCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final StatusCode statusCode;

    public BaseException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
