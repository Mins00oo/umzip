package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardHelpRepository extends JpaRepository<BoardHelp, Long> {

    @Query("SELECT b FROM BoardHelp b " +
            "WHERE ((:codeSmall = 401L AND b.codeSmall.id in (401L, 403L)) AND b.title LIKE %:title% AND b.sigungu = :sigungu)" +
            "OR ((:codeSmall = 402L AND b.codeSmall.id = 402L) AND b.title LIKE %:title% AND b.sigungu = :sigungu)" +
            "OR (:codeSmall = 0L AND b.title LIKE %:title% AND b.sigungu = :sigungu) ")
    Page<BoardHelp> findPageByTitleContainingAndSigunguAndCodeSmall(@Param("title") String title,
                                                                    @Param("sigungu") int sigungu,
                                                                    @Param("codeSmall") Long codeSmall,
                                                                    Pageable pageable);

    @Query("select b from BoardHelp b " +
            "where b.member.id = :memberId " +
            "and (b.codeSmall.id = 401L or b.codeSmall.id = 402L ) ")
    Page<BoardHelp> findAllByMemberIdMe(Long memberId, Pageable pageable);

    @Query("select b from BoardHelp b " +
            "where b.member.id = :memberId " +
            "and b.codeSmall.id = 403L ")
    Page<BoardHelp> findAllByMemberIdYou(Long memberId, Pageable pageable);
}
