package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.ReplyRequestDto;
import com.sparta.hanghaeboard.dto.ReplyResponseDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.entity.*;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.BoardRepository;
import com.sparta.hanghaeboard.repository.LikeRepository;
import com.sparta.hanghaeboard.repository.ReplyRepository;
import com.sparta.hanghaeboard.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final LikeRepository likeRepository;

    @Transactional
    public ReplyResponseDto createReply(Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = tokenValid(token);
        Board board = boardIdValid(id);
        User user = userValid(claims);
        Reply reply = new Reply(user, board, replyRequestDto.getContent());
        replyRepository.save(reply);

        return new ReplyResponseDto(reply);
    }

    @Transactional
    public ReplyResponseDto updateReply(Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = tokenValid(token);
        User user = userValid(claims);
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
    public statusCodeResponseDto deleteReply(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = tokenValid(token);
        User user = userValid(claims);
        if (UserRoleEnum.ADMIN.equals(user.getRole())) {
            Reply reply = replyIdValid(id);
            replyRepository.delete(reply);
            return new statusCodeResponseDto("?????? ?????? ??????", 200);
        }
        Reply reply = replyIdByUserValid(id, user);
        replyRepository.delete(reply);
        return new statusCodeResponseDto("?????? ?????? ??????", 200);
    }

    //?????????
    @Transactional
    public ReplyResponseDto likeReply(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken((request));
        Claims claims = tokenValid(token);
        User user = userValid(claims);
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("?????? ???????????? ???????????? ????????????.")
        );
        if (likeRepository.findByReplyIdAndUserId(id, user.getId()).isPresent()){
            reply.like((reply.getLikeCount()) - 1);
            likeRepository.deleteReplyLikeByUserId(user.getId());
        }else{
            reply.like((reply.getLikeCount()) + 1);
            ReplyLike replyLike = new ReplyLike(user, reply);
            likeRepository.save(replyLike);
        }
        return new ReplyResponseDto(reply);
    }


    //?????? ????????? ??????
    public Claims tokenValid(String token) {
        if (token != null) {
            if (!(jwtUtil.validateToken(token))) {
                throw new IllegalArgumentException("Token Error");
            }
        }
        return jwtUtil.getUserInfoFromToken(token);
    }

    //?????? ?????? ????????? ??????
    public User userValid(Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("???????????? ???????????? ????????????.")
        );
        return user;
    }

    //????????? ?????? ??????
    public Board boardIdValid(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("?????? ???????????? ???????????? ????????????.")
        );
        return board;
    }

    //?????? ?????? ??????
    public Reply replyIdValid(Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new NullPointerException("????????? ????????? ????????? ????????????")
        );
        return reply;
    }

    //reply ??????????????? ???????????? ??????
    public Reply replyIdByUserValid(Long id, User user) {
        Reply reply = replyRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("????????? ????????? ?????? ????????????")
        );
        return reply;
    }


}