package com.ssafy.umzip.domain.trade.service;

import com.ssafy.umzip.domain.code.entity.CodeSmall;
import com.ssafy.umzip.domain.code.repository.CodeSmallRepository;
import com.ssafy.umzip.domain.member.entity.Member;
import com.ssafy.umzip.domain.member.repository.MemberRepository;
import com.ssafy.umzip.domain.reviewreceiver.repository.ReviewReceiverRepository;
import com.ssafy.umzip.domain.trade.dto.*;
import com.ssafy.umzip.domain.trade.entity.BoardTrade;
import com.ssafy.umzip.domain.trade.entity.BoardTradeActive;
import com.ssafy.umzip.domain.trade.entity.BoardTradeImage;
import com.ssafy.umzip.domain.trade.repository.BoardTradeActiveRepository;
import com.ssafy.umzip.domain.trade.repository.BoardTradeCustomRepository;
import com.ssafy.umzip.domain.trade.repository.BoardTradeImageRepository;
import com.ssafy.umzip.domain.trade.repository.BoardTradeRepository;
import com.ssafy.umzip.global.common.Role;
import com.ssafy.umzip.global.common.StatusCode;
import com.ssafy.umzip.global.exception.BaseException;
import com.ssafy.umzip.global.util.s3.S3Service;
import com.ssafy.umzip.global.util.s3.S3UploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardTradeServiceImpl implements BoardTradeService {

    private final BoardTradeRepository boardTradeRepository;
    private final BoardTradeImageRepository boardTradeImageRepository;
    private final BoardTradeActiveRepository boardTradeActiveRepository;
    private final MemberRepository memberRepository;
    private final CodeSmallRepository codeSmallRepository;
    private final ReviewReceiverRepository reviewReceiverRepository;

    private final BoardTradeCustomRepository customRepository;

    private final S3Service s3Service;

    private final Long IS_ON_SALE = 301L;   // 판매중
    private final Long IS_COMPLETE_SALE = 302L; // 판매 완료

    @Transactional
    @Override
    public void PostBoardTrade(PostRequestDto requestDto,
                               List<MultipartFile> files,
                               Long memberId, int memberSigungu) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));
        
        CodeSmall codeSmall = codeSmallRepository.findById(IS_ON_SALE)
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

    @Transactional(readOnly = true)
    @Override
    public List<ListDto> listBoardTrade(ListRequestDto requestDto, Pageable pageable) {

        int curPage = pageable.getPageNumber() - 1;
        int size = pageable.getPageSize();

        memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        int sigungu = requestDto.getSigungu();
        String keyword = requestDto.getKeyword();

        List<ListDto> responseDto = customRepository.findBoardMatchingImageList(keyword, sigungu, IS_ON_SALE,
                PageRequest.of(curPage, size, Sort.Direction.DESC, "id"));

        return responseDto;
    }

    @Transactional
    @Override
    public DetailDto detailBoardTrade(DetailRequestDto requestDto) {

        Long curMemberId = requestDto.getMemberId();
        Long curBoardId = requestDto.getBoardId();
        memberRepository.findById(curMemberId)
                .orElseThrow(() -> new BaseException(StatusCode.NOT_VALID_MEMBER_PK));

        BoardTrade boardTrade = boardTradeRepository.findById(curBoardId)
                .orElseThrow(()-> new BaseException(StatusCode.NOT_VALID_BOARD_PK));

        boardTrade.setReadCnt(boardTrade.getReadCnt() + 1);

        Long boardWriterId = boardTrade.getMember().getId();

        Double rating = reviewReceiverRepository.findAverageScoreReceivedByMemberIdAndReceiverRole(boardWriterId, Role.USER)
                .orElse(0.0);

        boolean isWriter = false;
        if (Objects.equals(curMemberId, boardWriterId)) {
            isWriter = true;
        }

        boolean isActive = false;
        if (Objects.equals(boardTrade.getCodeSmall().getId(), IS_COMPLETE_SALE)) {
            // BoardTrade에는 302로 저장하지만, 보여줄 때는 302 또는 303으로 구분한다.
            BoardTradeActive boardTradeActive = boardTradeActiveRepository
                    .findByMemberIdAndBoardTradeId(curMemberId, curBoardId)
                    .orElseThrow(() -> new BaseException(StatusCode.NOT_PURCHASED_FROM_POST));

            if (boardTradeActive.getIsActive()) {
                isActive = true;
            }
        }

        List<BoardTradeImage> boardTradeImageList = boardTradeImageRepository.findAllByBoardTradeId(curBoardId);
        List<String> filePathList = new ArrayList<>();
        for (BoardTradeImage boardTradeImage : boardTradeImageList) {
            filePathList.add(boardTradeImage.getPath());
        }

        DetailDto responseDto = DetailDto.toDto(boardTrade, rating, filePathList, isWriter, isActive);


        return responseDto;
    }

    @Override
    public List<ProfileListDto> profileSellListBoardTrade(ProfileSellListRequestDto profileSellListRequestDto,
                                                          Pageable pageable) {
        
        // 현재 사용자의 프로필인가? 다른 사람의 프로필인가?
        if (profileSellListRequestDto.isSameMember()) {
            System.out.println("현재 사용자의 프로필 - [중고] 판매 목록");
        }

        int curPage = pageable.getPageNumber() - 1;
        int size = pageable.getPageSize();
        Long viewMemberId = profileSellListRequestDto.getViewMemberId();
        List<ProfileListDto> responseDto = customRepository.findProfileSellMatchingImageList(viewMemberId,
                PageRequest.of(curPage, size, Sort.Direction.DESC, "id"));

        return responseDto;
    }

    @Override
    public List<ProfileListDto> profileBuyListBoardTrade(ProfileBuyListRequestDto profileBuyListRequestDto, Pageable pageable) {
        if (profileBuyListRequestDto.isSameMember()) {
            System.out.println("현재 사용자의 프로필 - [중고] 구매 목록");
        }

        int curPage = pageable.getPageNumber() - 1;
        int size = pageable.getPageSize();
        Long viewMemberId = profileBuyListRequestDto.getViewMemberId();
        List<ProfileListDto> responseDto = customRepository.findProfileBuyMatchingImageList(viewMemberId,
                PageRequest.of(curPage, size, Sort.Direction.DESC, "id"));

        return responseDto;
    }
}
