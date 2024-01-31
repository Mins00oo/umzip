package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.help.service.BoardHelpService;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.trade.dto.PostRequestDto;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import com.ssafy.umzip.domain.trade.entity.BoardTradeImage;
import com.ssafy.umzip.domain.trade.repository.BoardTradeImageRepository;
import com.ssafy.umzip.domain.trade.repository.BoardTradeRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTradeServiceImpl implements BoardTradeService {

    private final BoardTradeRepository boardTradeRepository;
    private final BoardTradeImageRepository boardTradeImageRepository;
    private final MemberRepository memberRepository;
    private final CodeSmallRepository codeSmallRepository;

    private final S3Service s3Service;

    @Transactional
    @Override
    public void PostBoardTrade(PostRequestDto requestDto,
                               List<MultipartFile> files) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        Long codeSmallId = 301L;  // 판매중
        CodeSmall codeSmall = codeSmallRepository.findById(codeSmallId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));

        int readCnt = 0;
        BoardTrade boardTrade = requestDto.toEntity(requestDto, member, codeSmall, readCnt);
        boardTradeRepository.save(boardTrade);

        S3UploadDto s3UploadDto;
        for (MultipartFile file : files) {
            s3UploadDto = s3Service.upload(file, "umzip-service", "boardTrade");
            BoardTradeImage boardTradeImage = new BoardTradeImage(s3UploadDto, boardTrade);
            boardTradeImageRepository.save(boardTradeImage);
        }
    }
}
