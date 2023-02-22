package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.BoardRequestDto;
import com.sparta.boardhanghae.dto.BoardResponseDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.security.UserDetailsImpl;
import com.sparta.boardhanghae.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("") //게시글 전체
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")//게시글 선택
    public BoardResponseDto getDetailBoard(@PathVariable Long id) {
        return boardService.getDetailBoard(id);
    }

    @PostMapping("") //게시글 생성 api
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());
    }
    @PutMapping("/{id}") //수정 api
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getUser());
    }
    @DeleteMapping("/{id}")
    public statusCodeResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
    @PostMapping("/{id}/like")
    public BoardResponseDto likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.likeBoard(id, userDetails.getUser());
    }
}