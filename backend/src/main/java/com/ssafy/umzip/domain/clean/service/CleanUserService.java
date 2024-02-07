package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.clean.dto.user.*;
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
    List<CleanMatchingCompanyDto> companyListClean(CleanCompanyListRequestDto dto);
    CleanDetailResponseDto getCleanDetail(Long memberId,Long cleanId);
    Boolean completeReservation(Long mappingId,Long memberId);
}
