package com.ssafy.umzip.domain.help.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.help.dto.BoardHelpListDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpListRequestDto;
import com.ssafy.umzip.domain.help.dto.BoardHelpPostRequestDto;
import com.ssafy.umzip.domain.help.dto.CommentRequestDto;
import com.ssafy.umzip.domain.help.entity.BoardHelp;
import com.ssafy.umzip.domain.help.entity.BoardHelpComment;
import com.ssafy.umzip.domain.help.entity.BoardHelpImage;
import com.ssafy.umzip.domain.help.repository.BoardHelpImageRepository;
import com.ssafy.umzip.domain.help.repository.BoardHelpRepository;
import com.ssafy.umzip.domain.help.repository.CommentRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardHelpServiceImpl implements BoardHelpService {


    private final MemberRepository memberRepository;
    private final BoardHelpRepository boardHelpRepository;
    private final BoardHelpImageRepository boardHelpImageRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final CommentRepository commentRepository;

    private final S3Service s3Service;

    @Transactional
    @Override
    public void postBoardHelp(BoardHelpPostRequestDto requestDto, List<MultipartFile> files) {

        // Token에서 member_id와 sigungu(int)를 가져와서 사용한다.

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_EMAIL));

        CodeSmall codeSmall = codeSmallRepository.findById(requestDto.getCodeSmallId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_CODE));

        int curMemberSigungu = 100; // 작성자 시군구
        BoardHelp boardHelp = requestDto.toEntity(requestDto, member, curMemberSigungu, codeSmall);
        boardHelpRepository.save(boardHelp);

        S3UploadDto s3UploadDto;
        // files가 null이면( file을 첨부하지 않으면 ) 에러가 발생한다. -> if(!files.isEmpty()) 제외해봄
        for (MultipartFile file : files) {  // 하나씩 저장
            s3UploadDto = s3Service.upload(file, "umzip-service", "boardHelp");
            BoardHelpImage boardHelpImage = new BoardHelpImage(s3UploadDto, boardHelp);
            boardHelpImageRepository.save(boardHelpImage);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardHelpListDto> listBoardHelp(
            BoardHelpListRequestDto requestDto,
            @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        int curPage = pageable.getPageNumber() - 1;
        int size = pageable.getPageSize();


        // 시군구는 100이다.
        int sigungu = requestDto.getSigungu();
        String keyword = requestDto.getKeyword();
        Long codeSmallId = requestDto.getCodeSmallId();

        Page<BoardHelp> boards = boardHelpRepository
                .findPageByTitleContaining(keyword, PageRequest.of(curPage, size, Sort.Direction.DESC, "id")); // title 가져오기
        Page<BoardHelpListDto> boardDtoList = BoardHelpListDto.toDto(boards);


        // 2. comment 가져오기: group by board_help_id 를 이용해서 count 한 값을 가져옴


        return boardDtoList;
    }

    @Transactional
    @Override
    public void postComment(CommentRequestDto requestDto) {

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_EMAIL));

        BoardHelp boardHelp = boardHelpRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_EXIST_BOARD));

        BoardHelpComment comment = requestDto.toEntity(requestDto, boardHelp, member);
        commentRepository.save(comment);
    }


}
