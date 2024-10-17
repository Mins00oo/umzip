package com.ssafy.umzip.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("env")
@IfProfileValue(name = "spring.profiles.active", value = "env")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인 성공 테스트")
    @Transactional
    public void testLoginSuccess() throws Exception {
        // given
        String email = "user12345@umzip.com";
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member member = Member.builder()
                .email(email)
                .pwd(encodedPassword)
                .name("테스트유저")
                .point(300)
                .phone("911")
                .address("서울특별시")
                .sigungu(100)
                .addressDetail("구로구")
                .imageUrl("!")
                .build();

        // 회원 정보를 데이터베이스에 저장
        memberRepository.save(member);

        // 로그인 요청 데이터 생성
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", email);
        loginRequest.put("pwd", rawPassword);

        String requestBody = objectMapper.writeValueAsString(loginRequest);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.result.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.result.refreshToken").isNotEmpty());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    @Transactional
    public void testLoginFailure() throws Exception {
        // given
        String email = "user12345@umzip.com";
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member member = Member.builder()
                .email(email)
                .pwd(encodedPassword)
                .name("테스트유저")
                .point(300)
                .phone("911")
                .address("서울특별시")
                .sigungu(100)
                .addressDetail("구로구")
                .imageUrl("!")
                .build();

        // 회원 정보를 데이터베이스에 저장
        memberRepository.save(member);

        // 로그인 요청 데이터 생성
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", email);
        loginRequest.put("pwd", "잘못된 비밀번호");

        String requestBody = objectMapper.writeValueAsString(loginRequest);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(header().doesNotExist("Authorization"))
                .andExpect(jsonPath("$.isSuccess").value("false"))
                .andExpect(jsonPath("$.message").value("비밀번호 입력값이 잘못되었습니다."));
    }

}
