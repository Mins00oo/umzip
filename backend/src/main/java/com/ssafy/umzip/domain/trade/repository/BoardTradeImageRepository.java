package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import com.ssafy.umzip.domain.trade.entity.BoardTradeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardTradeImageRepository extends JpaRepository<BoardTradeImage, Long> {

    List<BoardTradeImage> findAllByBoardTradeId(Long boardTradeId);

    List<BoardTradeImage> findAll();

    @Query("select bti FROM BoardTradeImage bti JOIN FETCH bti.boardTrade where bti.boardTrade.id=:tradeId")
    List<BoardTradeImage> findAllById(Long tradeId);
}
