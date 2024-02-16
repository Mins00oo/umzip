package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.dto.ListDto;
import com.ssafy.umzip.domain.trade.dto.ProfileListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardTradeCustomRepository {

    List<ListDto> findBoardMatchingImageList(String title, int sigungu, Long codeSmallId, Pageable pageable);

    Page<ProfileListDto> findProfileSellMatchingImageList(Long memberId, Pageable pageable);

    Page<ProfileListDto> findProfileBuyMatchingImageList(Long memberId, Pageable pageable);
}
