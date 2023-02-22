package com.sparta.boardhanghae.service;

import com.sparta.boardhanghae.dto.BoardRequestDto;
import com.sparta.boardhanghae.dto.BoardResponseDto;
import com.sparta.boardhanghae.dto.ReplyResponseDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.entity.*;
import com.sparta.boardhanghae.jwt.JwtUtil;
import com.sparta.boardhanghae.repository.BoardLikeRepository;
import com.sparta.boardhanghae.repository.BoardRepository;
import com.sparta.boardhanghae.repository.ReplyRepository;
import com.sparta.boardhanghae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
    private final ReplyRepository replyRepository;
    private final BoardLikeRepository boardLikeRepository;

    //게시글 작성 서비스
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDTO, User user) {
        logger.info("BoardService createBoard 동작");
        Board board = new Board(boardRequestDTO, user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }


    //게시글 리스트에 담아서 뷰에 뿌리는 서비스
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        logger.info("BoardService getBoards 동작");
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDTO = new ArrayList<>();
        for (Board board : list) {
            boardResponseDTO.add(new BoardResponseDto(board));
        }
        return boardResponseDTO;
    }

    //상세페이지
    @Transactional(readOnly = true)
    public BoardResponseDto getDetailBoard(Long id) {
        logger.info("BoardService getDetailBoard 동작");
        Board board = boardIdValid(id);
//        BoardResponseDto boardResponseDTO = new BoardResponseDto(board);
        List<Reply> replyList = replyRepository.findAllByBoardIdOrderByModifiedAtDesc(id);
        List<ReplyResponseDto> replyResponseDto = new ArrayList<>();
        for (Reply reply : replyList){
            replyResponseDto.add(new ReplyResponseDto(reply));
        }
        return new BoardResponseDto(board, replyResponseDto);
    }


    //수정 서비스
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDTO, User user) {
        logger.info("BoardService updateBoard 동작");
        if (user.getRole() == UserRoleEnum.ADMIN) {
            Board board = boardIdValid(id);
            board.update(boardRequestDTO);
            BoardResponseDto boardResponseDTO = new BoardResponseDto(board);
            return boardResponseDTO;
        }
        Board board = boardIdByUserValid(id, user);
        board.update(boardRequestDTO, user);
        BoardResponseDto boardResponseDTO = new BoardResponseDto(board);
        return boardResponseDTO;
    }

    //삭제 서비스
    @Transactional
    public statusCodeResponseDto deleteBoard(Long id, User user) {
        logger.info("BoardService deleteBoard 동작");
        if (user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.delete(boardIdValid(id));
            return new statusCodeResponseDto("게시글 삭제 성공", 200);
        }
        Board board = boardIdByUserValid(id, user);

        boardRepository.delete(board);
        return new statusCodeResponseDto("게시글 삭제 성공", 200);
    }

    @Transactional
    public BoardResponseDto likeBoard(Long id, User user) {
        Board board = boardIdValid(id);
        if (boardLikeRepository.findByBoardIdAndUserId(id, user.getId()).isPresent()){
            board.like((board.getLikeCount()) - 1);
            boardLikeRepository.deleteBoardLikeByUserId(user.getId());
        }else{
            board.like((board.getLikeCount()) + 1);
            BoardLike boardLike = new BoardLike(user, board);
            boardLikeRepository.save(boardLike);
        }
        return new BoardResponseDto(board);
    }

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
}