package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.BoardRepository;
import com.sparta.hanghaeboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    //게시글 작성 서비스//2차 완료
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDTO, HttpServletRequest request) {
        logger.info("BoardService createBoard 동작");

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 글작성 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = new Board(boardRequestDTO, user); //받아온값 객체 생성해서 넣어 주고
            boardRepository.save(board); //레포지토리 save 함수를 통해 데이터 베이스에 저장
            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            return new BoardResponseDto(board);
        } else {
            throw new IllegalArgumentException("수정 안됨");
        }
    }


    //게시글 리스트에 담아서 뷰에 뿌리는 서비스
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() { //반환타입 체크
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDTO = new ArrayList<>();
        for (Board board : list) {
            boardResponseDTO.add(new BoardResponseDto(board));
        }
        return boardResponseDTO;
    }

    //상세페이지
    @Transactional(readOnly = true)
    public BoardResponseDto getDetailBoard(Long id) { //반환타입 체크
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
        );
        logger.info("상세페이지 board값: " + board.getId() + "/     " + board.getTitle());
        logger.info("상세보기");
        BoardResponseDto boardResponseDTO = new BoardResponseDto(board);
        return boardResponseDTO;
    }


    //수정 서비스
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto boardRequestDTO, HttpServletRequest request) {
        logger.info("BoardService updateBoard 동작");


        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("본인이 작성한 글이 아닙니다")
            );
            board.update(boardRequestDTO);
            BoardResponseDto boardResponseDTO = new BoardResponseDto(board);
            return boardResponseDTO;
        } else {
            return null;
        }
    }

    //삭제 서비스
    @Transactional
    public statusCodeResponseDto deleteBoard(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 삭제 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("본인이 작성한 글이 아닙니다")
            );
            boardRepository.delete(board);
            return new statusCodeResponseDto("게시글 삭제 성공", 200);
        } else {
            return new statusCodeResponseDto("게시글 삭제 실패", 87);
        }
    }
}

//중복 코드 아래 정리 필요