package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.BoardRequestDto;
import com.sparta.boardhanghae.dto.BoardResponseDto;
import com.sparta.boardhanghae.dto.ReplyResponseDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.update(id, boardRequestDto, request);
    }
    @DeleteMapping("/{id}")
    public statusCodeResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }
    @PostMapping("/{id}/like")
    public BoardResponseDto likeBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.likeBoard(id, request);
    }
}