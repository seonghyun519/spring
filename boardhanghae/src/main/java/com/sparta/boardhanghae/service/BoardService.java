package com.sparta.boardhanghae.service;

import com.sparta.boardhanghae.dto.*;
import com.sparta.boardhanghae.entity.*;
import com.sparta.boardhanghae.jwt.JwtUtil;
import com.sparta.boardhanghae.repository.*;
import com.sparta.boardhanghae.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
    private final ReplyRepository replyRepository;
    private final BoardLikeRepository boardLikeRepository;

    //S3 Test
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;

    //게시글 작성 서비스
    @Transactional
    public StatusCodeResponseDto<BoardResponseDto> createBoard(BoardRequestDto boardRequestDTO, User user) {
        logger.info("BoardService createBoard 동작");
        Board board = new Board(boardRequestDTO, user);
        boardRepository.save(board);
        return StatusCodeResponseDto.ok(new BoardResponseDto(board));
    }
    //게시글 리스트에 담아서 뷰에 뿌리는 서비스
    @Transactional(readOnly = true)
    public StatusCodeResponseDto<List<BoardResponseDto>> getBoards() {
        logger.info("BoardService getBoards 동작");
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDTO = new ArrayList<>();
        for (Board board : list) {
            boardResponseDTO.add(new BoardResponseDto(board));
        }
        return StatusCodeResponseDto.ok(boardResponseDTO);
    }
    //상세페이지
    @Transactional(readOnly = true)
    public StatusCodeResponseDto<BoardResponseDto> getDetailBoard(Long id) {
        logger.info("BoardService getDetailBoard 동작");
        List<Reply> replyList = replyRepository.findAllByBoardIdOrderByModifiedAtDesc(id); //수정된 시간 기준 내림차순 정렬
        List<ReplyResponseDto> replyResponseDto = new ArrayList<>();
        for (Reply reply : replyList){
            replyResponseDto.add(new ReplyResponseDto(reply));
        }
        return StatusCodeResponseDto.ok(new BoardResponseDto(boardIdValid(id), replyResponseDto));
    }


    //수정 서비스
    @Transactional
    public StatusCodeResponseDto<BoardResponseDto> update(Long id, BoardRequestDto boardRequestDTO, User user) {
        logger.info("BoardService updateBoard 동작");
        if (user.getRole() == UserRoleEnum.ADMIN) {
            Board board = boardIdValid(id);
            board.update(boardRequestDTO);
            return StatusCodeResponseDto.ok(new BoardResponseDto(board));
        }
        Board board = boardIdByUserValid(id, user);
        board.update(boardRequestDTO, user);
        return StatusCodeResponseDto.ok(new BoardResponseDto(board));
    }
    //삭제 서비스
    @Transactional
    public StatusCodeResponseDto<String> deleteBoard(Long id, User user) {
        logger.info("BoardService deleteBoard 동작");
        if (user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.delete(boardIdValid(id));
            return StatusCodeResponseDto.ok("성공");
        }
        Board board = boardIdByUserValid(id, user);

        boardRepository.delete(board);
        return StatusCodeResponseDto.ok("게시글 삭제 성공");
    }

    @Transactional
    public StatusCodeResponseDto<String> likeBoard(Long id, User user) {
        Board board = boardIdValid(id);
        if (boardLikeRepository.findByBoardIdAndUserId(id, user.getId()).isPresent()){
            board.like((board.getLikeCount()) - 1);
            boardLikeRepository.deleteBoardLikeByUserId(user.getId());
            return StatusCodeResponseDto.ok("좋아요 삭제");
        }
            board.like((board.getLikeCount()) + 1);
            BoardLike boardLike = new BoardLike(user, board);
            boardLikeRepository.save(boardLike);
        return StatusCodeResponseDto.ok("좋아요 성공");
    }
    
    //중복 코드 메서드
    //board 게시글의 유저정보와 동일여부 검사
    public Board boardIdByUserValid(Long id, User user) {
        Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("본인이 작성한 글이 아닙니다")
        );
        return board;
    }
    //게시글 유무 확인
    public Board boardIdValid(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        return board;
    }

    public ResponseDto<?> addPost(PostRequestDto requestDto) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        postRepository.save(new Post(imageUrl));

        return ResponseDto.success("게시물 등록 성공");
    }
}