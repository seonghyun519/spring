package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor //final이 붙거나@NotNull이 붙은 필드의 생성자를 자동생성해주는 어노테이션
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @GetMapping("") //게시글 전체
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")//게시글 선택
    public BoardResponseDto getDetailBoard(@PathVariable Long id) {
        return boardService.getDetailBoard(id);
    }

    @PostMapping("") //게시글 생성 api
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.createBoard(boardRequestDto, request);
    }
    @PutMapping("/{id}") //수정 api
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {//@PathVariable값을 받아오고 수정 내용을 받기 위해 @RequestBody를 통해 Body부분의 데이터를 가져오고 username과 contents 두개뿐이니 앞서 만든 RequestDto를 그대로 사용
        return boardService.update(id, boardRequestDto, request); // 보드서비스 클래스에 update 메서드에 id 값 필요하며 어떤값으로 변경되는지 알기 위해 requestDto 파라미터 두개(@RequestBody 바디 가져오기)
    }
    @DeleteMapping("/{id}")
    public statusCodeResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
}