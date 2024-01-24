package com.ssafy.umzip.domain.help.service;


import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

public interface BoardHelpService {
    
    Page<BoardHelpListDto> retrieveList(
            BoardHelpListRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable);

}
