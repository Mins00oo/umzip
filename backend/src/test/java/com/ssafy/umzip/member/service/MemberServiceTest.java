package com.ssafy.umzip.member.service;

import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.member.service.MemberServiceImpl;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.reviewreceiver.service.ReviewReceiverService;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

        log.info("Exception: ", exception);

        // 예외 메시지나 코드 검증 (필요한 경우)
        assertEquals(StatusCode.NOT_VALID_MEMBER_PK, exception.getStatusCode());
    }
}
