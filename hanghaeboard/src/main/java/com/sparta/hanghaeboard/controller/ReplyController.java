package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.*;
import com.sparta.hanghaeboard.entity.Reply;
import com.sparta.hanghaeboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{id}/reply")
    public ReplyResponseDto createReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.createReply(id, replyRequestDto, request);
    }

    @PutMapping("/{id}/reply")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        return replyService.updateReply(id, replyRequestDto, request);
    }

    @DeleteMapping("/{id}/reply")
    public statusCodeResponseDto deleteReply(@PathVariable Long id, HttpServletRequest request) {
        return replyService.deleteReply(id, request);
    }

    @PostMapping("/{id}/reply/like")
    public ReplyResponseDto likeReply(@PathVariable Long id, HttpServletRequest request) {
        return replyService.likeReply(id, request);
    }
}