package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.trade.dto.PostRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardTradeService {

    void PostBoardTrade(PostRequestDto requestDto, List<MultipartFile> files);
}
