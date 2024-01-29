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
    Page<BoardHelp> findPageByTitleContaining(String title, Pageable pageable);

    Page<BoardHelp> findPageByTitleContainingAndSigungu(String title, int sigungu, Pageable pageable);

    @Query("SELECT b FROM BoardHelp b " +
            "WHERE ((:codeSmall = 401L AND b.codeSmall.id in (401L, 403L)) AND b.title LIKE %:title% AND b.sigungu = :sigungu)" +
            "OR ((:codeSmall = 402L AND b.codeSmall.id = 402L) AND b.title LIKE %:title% AND b.sigungu = :sigungu)" +
            "OR (:codeSmall = 0L AND b.title LIKE %:title% AND b.sigungu = :sigungu) ")
    Page<BoardHelp> findPageByTitleContainingAndSigunguAndCodeSmall(@Param("title") String title,
                                                                    @Param("sigungu") int sigungu,
                                                                    @Param("codeSmall") Long codeSmall,
                                                                    Pageable pageable);
}
