package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.*;
import com.sparta.hanghaeboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/reply/{id}")
    public statusCodeResponseDto createReply(@PathVariable Long id, String content, HttpServletRequest request){
        return replyService.createReply(id, content, request);
    }

    @PutMapping ("/reply/{id}")
    public statusCodeResponseDto updateReply(@PathVariable Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request){
        return replyService.updateReply(id, replyRequestDto, request);
    }

    @DeleteMapping("/reply/{id}")
    public statusCodeResponseDto deleteReply(@PathVariable Long id, HttpServletRequest request){
        return replyService.deleteReply(id, request);
    }
    }