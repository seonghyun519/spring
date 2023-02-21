package com.sparta.boardhanghae.service;

import com.sparta.boardhanghae.dto.ReplyRequestDto;
import com.sparta.boardhanghae.dto.ReplyResponseDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.entity.*;
import com.sparta.boardhanghae.jwt.JwtUtil;
import com.sparta.boardhanghae.repository.BoardRepository;
import com.sparta.boardhanghae.repository.ReplyLikeRepository;
import com.sparta.boardhanghae.repository.ReplyRepository;
import com.sparta.boardhanghae.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final ReplyLikeRepository replyLikeRepository;

    @Transactional
    public ReplyResponseDto createReply(Long id, ReplyRequestDto replyRequestDto, User user) {
        Board board = boardIdValid(id);
        Reply reply = new Reply(user, board, replyRequestDto.getContent());
        replyRepository.save(reply);

        return new ReplyResponseDto(reply);
    }

    @Transactional
    public ReplyResponseDto updateReply(Long id, ReplyRequestDto replyRequestDto, User user) {
        if (UserRoleEnum.ADMIN.equals(user.getRole())) {
            Reply reply = replyIdValid(id);
            reply.update(replyRequestDto);
            return new ReplyResponseDto(reply);
        }
        Reply reply = replyIdByUserValid(id, user);
        reply.update(replyRequestDto);
        return new ReplyResponseDto(reply);
    }

    @Transactional
    public statusCodeResponseDto deleteReply(Long id, User user) {
        if (UserRoleEnum.ADMIN.equals(user.getRole())) {
            Reply reply = replyIdValid(id);
            replyRepository.delete(reply);
            return new statusCodeResponseDto("댓글 삭제 성공", 200);
        }
        Reply reply = replyIdByUserValid(id, user);
        replyRepository.delete(reply);
        return new statusCodeResponseDto("댓글 삭제 성공", 200);
    }

    //좋아요
    @Transactional
    public ReplyResponseDto likeReply(Long id, User user) {
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (replyLikeRepository.findByReplyIdAndUserId(id, user.getId()).isPresent()){
            reply.like((reply.getLikeCount()) - 1);
            replyLikeRepository.deleteReplyLikeByUserId(user.getId());
        }else{
            reply.like((reply.getLikeCount()) + 1);
            ReplyLike replyLike = new ReplyLike(user, reply);
            replyLikeRepository.save(replyLike);
        }
        return new ReplyResponseDto(reply);
    }

    //게시글 유무 확인
    public Board boardIdValid(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        return board;
    }
    //댓글 유무 확인
    public Reply replyIdValid(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new NullPointerException("본인이 작성한 댓글이 아닙니다")
        );
        return reply;
    }

    //reply 유저정보와 동일여부 검사
    public Reply replyIdByUserValid(Long id, User user) {
        Reply reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("본인이 작성한 글이 아닙니다")
        );
        return reply;
    }
}