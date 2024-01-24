package com.ssafy.umzip.domain.help.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpPostRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.help.repository.BoardHelpImageRepository;
import com.ssafy.umzip.domain.help.repository.BoardHelpRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardHelpServiceImpl implements BoardHelpService {


    private final MemberRepository memberRepository;
    private final BoardHelpRepository boardHelpRepository;
    private final BoardHelpImageRepository boardHelpImageRepository;
    private final CodeSmallRepository codeSmallRepository;

    private final S3Service s3Service;

    /*
     도움 게시글을 작성하는 메소드
     */
    @Transactional
    @Override
    public void postBoardHelp(BoardHelpPostRequestDto requestDto, List<MultipartFile> files) {

        // Token에서 member_id와 sigungu(int)를 가져와서 사용한다.

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_EMAIL));

        CodeSmall codeSmall = codeSmallRepository.findById(requestDto.getCodeSmallId())
                .orElseThrow(() -> new BaseException(StatusCode.CODE_DOES_NOT_EXIST));

        int curMemberSigungu = 100; // 작성자 시군구
        BoardHelp boardHelp = requestDto.toEntity(requestDto, member, curMemberSigungu, codeSmall);
        boardHelpRepository.save(boardHelp);

        S3UploadDto s3UploadDto;
        if (!files.isEmpty()) {
            for (MultipartFile file : files) {  // 하나씩 저장
                s3UploadDto = s3Service.upload(file, "umzip-service", "boardHelp");
                BoardHelpImage boardHelpImage = new BoardHelpImage(s3UploadDto, boardHelp);
                boardHelpImageRepository.save(boardHelpImage);
            }
        }
    }

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
