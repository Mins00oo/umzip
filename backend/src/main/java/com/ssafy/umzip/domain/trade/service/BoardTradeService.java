package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.trade.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardTradeService {

    void PostBoardTrade(PostRequestDto requestDto, List<MultipartFile> files, Long memberId, int memberSigungu);

    List<ListDto> listBoardTrade(ListRequestDto requestDto, Pageable pageable);

    DetailDto detailBoardTrade(DetailRequestDto requestDto);

    List<ProfileListDto> profileSellListBoardTrade(ProfileSellListRequestDto profileSellListRequestDto, Pageable pageable);

    List<ProfileListDto> profileBuyListBoardTrade(ProfileBuyListRequestDto profileBuyListRequestDto, Pageable pageable);

    void CompleteSale(CompleteSaleRequestDto requestDto);
}
