package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardHelpRepository extends JpaRepository<BoardHelp, Long> {
    
    // page: 0부터 시작
    // size: 페이지당 데이터수
    // PageRequest.of(page, size, Direction)
    Page<BoardHelp> findAllByTitleContaining(String title, @NotNull Pageable pageable);    // 전체 가져오기
    Page<BoardHelp> findPageByTitleContaining(String title, Pageable pageable);


//    @Query("SELECT b FROM BoardHelp b WHERE b.sigungu = :sigungu AND b.title LIKE %:title% AND " +
//            "(b.codeSmall.id = 401 OR b.codeSmall.id = 403 OR (:codeSmallId = 0 AND b.codeSmall.id IN (401, 402, 403)) OR " +
//            "(:codeSmall = 401 AND b.codeSmall.id IN (401, 403)) OR (:codeSmall = 402 AND b.codeSmall.id = 402))")

//    @Query("SELECT new com.ssafy.umzip.domain.help.dto.BoardHelpListDto" +
//            "(b.id, b.member.name, b.title, b.codeSmall.id, b.createDt, COUNT(c.id), b.readCnt, b.point) " +
//            "FROM BoardHelp b " +
//            "LEFT JOIN b.commentList c " +
//            "WHERE b.sigungu = :sigungu AND b.title LIKE %:title% " +
//            "AND (:codeSmallId = 0 AND b.codeSmall.id IN (401, 402, 403) " +
//            "OR (:codeSmallId = 401 AND b.codeSmall.id IN (401, 403)) " +
//            "OR (:codeSmallId = 403 AND b.codeSmall.id = 403)) " +
//            "GROUP BY b.id")
//    Page<BoardHelpListDto> findBySigunguAndTitleContainingAndCodeSmall(
//            @Param("sigungu") int sigungu,
//            @Param("title") String title,
//            @Param("codeSmallId") Long codeSmallId,
//            Pageable pageable);
}
