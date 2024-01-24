package com.ssafy.umzip.domain.help.service;

import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.repository.BoardHelpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardHelpServiceImpl implements BoardHelpService {

    private final BoardHelpRepository boardHelpRepository;

    /*
    도움 게시글 리스트를 현재 사용자의 시/군/구에 맞게 가져오는 메소드
    시군구와 category( 도와줘요(401), 도와줄게요(402), 도와줬어요(403) ), pageable을 인자로 받는다.
    */
    @Transactional(readOnly = true)
    @Override
    public Page<BoardHelpListDto> retrieveList(
            BoardHelpListRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {



        // repository 접근 후 Page<BoardHelp>를 가져온다.
        // 가져오지 못한 경우 예외처리
        Page<BoardHelpListDto> boardPage = boardHelpRepository.findBySigungu(requestDto.getSigungu(), pageable);

        return boardPage;
    }
}
