package com.ssafy.umzip.domain.help.repository;

import com.ssafy.umzip.domain.help.dto.ListHelpDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardHelpCustomRepository {

    Page<ListHelpDto> listBoardHelpAndCommentCount(String title, int sigungu,
                                                   Long codeSmallId, Pageable pageable);
}
