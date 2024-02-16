package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.trade.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardTradeService {

    void PostBoardTrade(PostRequestDto requestDto, List<MultipartFile> files, Long memberId, int memberSigungu);

    List<ListDto> listBoardTrade(ListRequestDto requestDto, Pageable pageable);

    DetailDto detailBoardTrade(DetailRequestDto requestDto);

    Page<ProfileListDto> profileSellListBoardTrade(ProfileSellListRequestDto profileSellListRequestDto, Pageable pageable);

    Page<ProfileListDto> profileBuyListBoardTrade(ProfileBuyListRequestDto profileBuyListRequestDto, Pageable pageable);

    void CompleteSale(CompleteRequestDto requestDto);

    void CompleteBuy(CompleteRequestDto requestDto);
}
