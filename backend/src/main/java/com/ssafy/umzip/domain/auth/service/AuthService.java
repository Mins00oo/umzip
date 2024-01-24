package com.ssafy.umzip.domain.auth.service;

import com.ssafy.umzip.domain.auth.dto.AuthCodeRequestDto;
import com.ssafy.umzip.domain.auth.dto.AuthEmailRequestDto;

public interface AuthService {
    void sendCode(AuthCodeRequestDto codeRequestDto);

    void authCode(AuthCodeRequestDto codeRequestDto);

    void authEmail(AuthEmailRequestDto emailRequestDto);
}
