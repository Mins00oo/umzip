package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.trade.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardTradeService {

    void PostBoardTrade(PostRequestDto requestDto, List<MultipartFile> files, Long memberId, int memberSigungu);

    List<BoardTradeListDto> listBoardTrade(ListRequestDto requestDto, Pageable pageable);

    DetailDto detailBoardTrade(DetailRequestDto requestDto);

    Page<ProfileSellListDto> profileSellListBoardTrade(ProfileSellListRequestDto profileSellListRequestDto, Pageable pageable);

    Page<ProfileBuyListDto> profileBuyListBoardTrade(ProfileBuyListRequestDto profileBuyListRequestDto, Pageable pageable);
}
