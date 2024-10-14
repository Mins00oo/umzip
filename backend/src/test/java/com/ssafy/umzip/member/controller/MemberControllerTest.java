package com.ssafy.umzip.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.umzip.domain.member.controller.MemberController;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.dto.MemberResponseDto;
import com.ssafy.umzip.domain.member.service.MemberService;
import com.ssafy.umzip.global.util.jwt.JwtTokenProvider;
import com.ssafy.umzip.global.util.security.CustomAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CustomAuthenticationFilter.class})
})
@WithMockUser
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberService memberService;


    @Test
    @DisplayName("ID별 회원 조회")
    public void testRetrieveUser() throws Exception {

        // given
        Long memberId = 1L;

        Long requestId = 2L;

        MemberResponseDto responseDto = MemberResponseDto.builder()
                .me(true)
                .name("John Doe")
                .phone("123-456-7890")
                .point(100)
                .email("john.doe@example.com")
                .avgScore("4.5")
                .tagList(new ArrayList<>())
                .imageUrl("http://example.com/image.jpg")
                .build();

        // when
        when(jwtTokenProvider.getId(any(HttpServletRequest.class))).thenReturn(requestId);

        when(memberService.retrieveMember(memberId, requestId)).thenReturn(responseDto);

        // then
        mockMvc.perform(get("/api/users/" + memberId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.me").value(responseDto.isMe()))
                .andExpect(jsonPath("$.result.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.result.phone").value(responseDto.getPhone()))
                .andExpect(jsonPath("$.result.point").value(responseDto.getPoint()))
                .andExpect(jsonPath("$.result.email").value(responseDto.getEmail()))
                .andExpect(jsonPath("$.result.avgScore").value(responseDto.getAvgScore()))
                .andExpect(jsonPath("$.result.imageUrl").value(responseDto.getImageUrl()))
                .andDo(print());

        // assert
        verify(memberService, times(1)).retrieveMember(memberId, requestId);
    }

    @Test
    @DisplayName("회원가입")
    public void createMember() throws Exception {
        MemberCreateRequestDto requestDto = new MemberCreateRequestDto();
        requestDto.setName("홍길동");
        requestDto.setEmail("hong@example.com");
        requestDto.setPhone("010-1234-5678");
        requestDto.setPassword("password123");
        requestDto.setAddress("서울시 강남구");
        requestDto.setSigungu(1);
        requestDto.setAddressDetail("101호");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestDto);

        // then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다."))
                .andExpect(jsonPath("$.code").value(100))
                .andDo(print());

        // assert
        verify(memberService, times(1)).createMember(any(MemberCreateRequestDto.class));

    }
}
