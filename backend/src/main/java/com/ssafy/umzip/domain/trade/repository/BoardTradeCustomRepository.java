package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.dto.ListDto;
import com.ssafy.umzip.domain.trade.dto.ProfileListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardTradeCustomRepository {

    List<ListDto> findBoardMatchingImageList(String title, int sigungu, Long codeSmallId, Pageable pageable);

    List<ProfileListDto> findProfileSellMatchingImageList(Long memberId, Pageable pageable);

    List<ProfileListDto> findProfileBuyMatchingImageList(Long memberId, Pageable pageable);
}
