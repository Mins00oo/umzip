package com.ssafy.umzip.domain.clean.service;

import com.ssafy.umzip.domain.clean.dto.user.CleanReservationCompanyDto;
import com.ssafy.umzip.domain.clean.dto.user.CleanReservationRequestDto;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CleanUserService {
    void createClean(List<CleanReservationCompanyDto> companys,
                     List<MultipartFile> imageFileList,
                     Long price,
                     CleanReservationRequestDto reservationRequestDto,
                     Long memberId
    );
}
