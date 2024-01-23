package com.ssafy.umzip.domain.auth.service;

import com.ssafy.umzip.domain.auth.dto.AuthCodeRequestDto;

public interface AuthService {
    void sendCode(AuthCodeRequestDto codeRequestDto);
}
