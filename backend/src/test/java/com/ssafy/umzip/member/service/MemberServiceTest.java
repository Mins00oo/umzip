package com.ssafy.umzip.member.service;

import com.ssafy.umzip.domain.company.dto.MostTagResponseDto;
import com.ssafy.umzip.domain.member.dto.MemberCreateRequestDto;
import com.ssafy.umzip.domain.member.dto.MemberResponseDto;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.member.service.MemberServiceImpl;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewreceiver.service.ReviewReceiverService;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MemberServiceTest {
    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReviewReceiverRepository reviewReceiverRepository;

    @Mock
    private ReviewReceiverService reviewReceiverService;

    @Mock
    private BCryptPasswordEncoder encoder;


    @Test
    @DisplayName("예외테스트_올바르지않은 PK")
    void testException_MemberNotFound() {
        // given
        Long memberId = 1L;
        Long requestId = 2L; // 요청한 사용자 ID (테스트 목적에 맞게 설정)

        // when
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // then
        BaseException exception = assertThrows(BaseException.class, () -> {
            memberService.retrieveMember(memberId, requestId);
        });

        // 예외 메시지나 코드 검증 (필요한 경우)
        assertEquals(StatusCode.NOT_VALID_MEMBER_PK, exception.getStatusCode());
    }

    @Test
    @DisplayName("회원조회 서비스 테스트")
    void test_RetrieveMember() {
        // given
        Long memberId = 100L;
        Long requestId = 1L;

        Member member = Member.builder()
                .name("홍길동")
                .email("umzip@naver.com")
                .phone("111-1111-1111")
                .point(100)
                .imageUrl("https://images.url")
                .addressDetail("상세주소")
                .build();

        member.setId(memberId);

        List<MostTagResponseDto> tagList = Arrays.asList(
                new MostTagResponseDto("친절해요", 1),
                new MostTagResponseDto("일이 빨라요", 2),
                new MostTagResponseDto("가격이 쌉니다", 12)
        );
        String avgScore = "4.7";

        // when
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(reviewReceiverRepository.findTopTagsByMemberId(memberId, 3, Role.USER)).thenReturn(tagList);
        when(reviewReceiverService.receiverReviewScore(memberId, Role.USER)).thenReturn(avgScore);

        MemberResponseDto result = memberService.retrieveMember(memberId, requestId);

        // then
        assertEquals("홍길동", result.getName());
        assertFalse(result.isMe());
        assertEquals("umzip@naver.com", result.getEmail());
        assertNull(result.getPhone());
        assertEquals(100, result.getPoint());
        assertEquals("https://images.url", result.getImageUrl());
        assertEquals("4.7", result.getAvgScore());
        assertEquals(3, result.getTagList().size());
        assertEquals("친절해요", result.getTagList().get(0).getTagName());
        assertEquals("일이 빨라요", result.getTagList().get(1).getTagName());

        // verify
        verify(memberRepository).findById(memberId);
        verify(reviewReceiverRepository).findTopTagsByMemberId(memberId, 3, Role.USER);
        verify(reviewReceiverService).receiverReviewScore(memberId, Role.USER);

    }

    @Test
    @DisplayName("회원가입 서비스 테스트")
    void test_createMember() {
        //given
        String rawPassword = "1234";
        String encodedPassword = "enc1234";

        MemberCreateRequestDto requestDto = new MemberCreateRequestDto();

        requestDto.setName("홍길동");
        requestDto.setEmail("admin@umzip.com");
        requestDto.setPassword(rawPassword);

        Member member = MemberCreateRequestDto.toEntity(requestDto, encodedPassword, 1000);

        // when
        when(encoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member createdMember = memberService.createMember(requestDto);

        // then
        assertNotNull(createdMember);
        assertEquals("admin@umzip.com", createdMember.getEmail());
        assertEquals("enc1234", createdMember.getPwd());

        // verify
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(encoder, times(1)).encode(rawPassword);
    }
}
