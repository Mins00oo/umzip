package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.entity.BoardHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardHelpRepository extends JpaRepository<BoardHelp, Long> {

    @Query("select b from BoardHelp b " +
            "where b.member.id = :memberId " +
            "and (b.codeSmall.id = 401L or b.codeSmall.id = 402L ) ")
    Page<BoardHelp> findAllByMemberIdMe(Long memberId, Pageable pageable);

    @Query("select b from BoardHelp b " +
            "where b.member.id = :memberId " +
            "and b.codeSmall.id = 403L ")
    Page<BoardHelp> findAllByMemberIdYou(Long memberId, Pageable pageable);
}
