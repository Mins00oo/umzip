package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.clean.dto.user.CleanDetailResponseDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationCompanyDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationRequestDto;
import com.ssafy.umzip.domain.clean.dto.user.UserCleanReservationResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CleanUserService {
    void createClean(List<CleanReservationCompanyDto> companys,
                     List<MultipartFile> imageFileList,
                     Long price,
                     CleanReservationRequestDto reservationRequestDto,
                     Long memberId
    );
    List<UserCleanReservationResponseDto> userReservationClean(Long memberId);
    Boolean cancelClean(Long mappingId,Long memberId);
    CleanDetailResponseDto getCleanDetail(Long memberId,Long cleanId);
}
