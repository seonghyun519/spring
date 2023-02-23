package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.ReplyRequestDto;
import com.sparta.boardhanghae.dto.ReplyResponseDto;
import com.sparta.boardhanghae.dto.StatusCodeResponseDto;
import com.sparta.boardhanghae.security.UserDetailsImpl;
import com.sparta.boardhanghae.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{id}/reply")
    public ReplyResponseDto createReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.createReply(id, replyRequestDto, userDetails.getUser());
    }

    @PutMapping("/{id}/reply")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.updateReply(id, replyRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{id}/reply")
    public StatusCodeResponseDto deleteReply(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.deleteReply(id, userDetails.getUser());
    }

    @PostMapping("/{id}/reply/like")
    public ReplyResponseDto likeReply(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.likeReply(id, userDetails.getUser());
    }
}