package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.trade.dto.BoardTradeListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardTradeCustomRepository {

    List<BoardTradeListDto> findBoardMatchingImageList(String title, int sigungu, Long codeSmallId, Pageable pageable);
}
