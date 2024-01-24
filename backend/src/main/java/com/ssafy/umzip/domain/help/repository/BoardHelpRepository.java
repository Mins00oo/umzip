package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHelpRepository extends JpaRepository<BoardHelp, Long> {

    Page<BoardHelpListDto> findBySigungu(int sigungu, Pageable pageable);

}
