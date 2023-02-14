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
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDTO, HttpServletRequest request) { //데이터 베이스에 연결하여 저장하려면 Entity어노테이션이 걸려있는 Memo클래스를 인스턴스로 만들어서 그값을 사용해서 저장해야함
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
    //import org.springframework.transaction.annotation.Transactional; 이전 import에서 적용안되서 바꿈..
    public List<BoardResponseDto> getBoards() { //반환타입 체크
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDTO = new ArrayList<>();
        for (Board board : list) {
            boardResponseDTO.add(new BoardResponseDto(board));
        }
        logger.info("데이터베이스에 데이터를 뷰에 뿌려주기 위해 리스트에 담아 뿌려주는 서비스");
//        return memoRepository.findAll(); //가장 최근에 저장된순으로 보여줘야하기 때문에 findAll() 사용하지 않고 다른방식으로 불러와야함 레포지토리에서 설정해줘야함.
        //list에 담는거는 레포지토리에서 함수 구성
        return boardResponseDTO;
        //레포지토리에 생성한 함수?? 불러옴
    }

    //상세페이지
    @Transactional(readOnly = true)
    //import org.springframework.transaction.annotation.Transactional; 이전 import에서 적용안되서 바꿈..
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
//        Board board = boardRepository.findById(id).orElseThrow(
//                () ->new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        ); //확인절차 후 오류가 발생하지 않으면 아래 동작
//        if (board.getPwd().equals(boardRequestDTO.getPwd())){
//        }else {
//            new IllegalArgumentException("비밀번호가 틀렸습니다.");
//        }


        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
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
//            findByIdAndUserId(Long id, Long clientId);
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

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
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