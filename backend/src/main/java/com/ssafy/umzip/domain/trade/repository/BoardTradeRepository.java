package com.ssafy.umzip.domain.trade.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardTradeRepository extends JpaRepository<BoardTrade, Long> {

    @Query("select b from BoardTrade b " +
            "where (b.codeSmall.id = :codeSmallId and b.title like %:title% and (b.isDirect = true and b.sigungu = :sigungu)) " +
            "or (b.codeSmall.id = :codeSmallId and b.title like %:title% and b.isDirect = false)")
    Slice<BoardTrade> findBoardTradeList(@Param("title") String title, @Param("sigungu") int sigungu,
                           @Param("codeSmallId") Long codeSmallId, Pageable pageable);


}
