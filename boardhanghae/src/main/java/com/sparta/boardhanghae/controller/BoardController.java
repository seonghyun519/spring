package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.*;
import com.sparta.boardhanghae.security.UserDetailsImpl;
import com.sparta.boardhanghae.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("") //게시글 전체
    public StatusCodeResponseDto<List<BoardResponseDto>> getBoard() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")//게시글 선택
    public StatusCodeResponseDto<BoardResponseDto> getDetailBoard(@PathVariable Long id) {
        return boardService.getDetailBoard(id);
    }

    @PostMapping("") //게시글 생성 api
    public StatusCodeResponseDto<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());
    }
    @PutMapping("/{id}") //수정 api
    public StatusCodeResponseDto<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, boardRequestDto, userDetails.getUser());
    }
    @DeleteMapping("/{id}")
    public StatusCodeResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
    @PostMapping("/{id}/liked")
    public StatusCodeResponseDto<String> likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.likeBoard(id, userDetails.getUser());
    }
    @PostMapping("/add")
    public ResponseDto<?> addPost(@RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        PostRequestDto requestDto = new PostRequestDto(multipartFile);
        return boardService.addPost(requestDto);
    }
}