package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.ReplyRequestDto;
import com.sparta.hanghaeboard.dto.ReplyResponseDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Reply;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.BoardRepository;
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

    @Transactional
    public statusCodeResponseDto createReply(Long id, String content, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                Board board = boardRepository.findById(id).orElseThrow(() -> new NullPointerException("등록되지 않은 게시글입니다."));
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
                Reply reply = new Reply(user, board, content);
                replyRepository.save(reply);
                new ReplyResponseDto(reply);
                return new statusCodeResponseDto("댓글 게시 성공", 200);
            }
        }
        throw new IllegalArgumentException("Token Error");
    }

    @Transactional
    public statusCodeResponseDto updateReply(Long id, ReplyRequestDto replyRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(()-> new IllegalArgumentException("용자가 존재하지 않습니다."));
                Reply reply= replyRepository.findById(id).orElseThrow(()-> new NullPointerException("해당 게시글이 존재하지 않습니다."));

                if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(reply.getUser().getUsername())){
                    reply.update(replyRequestDto);
                    new ReplyResponseDto(reply);
                    return new statusCodeResponseDto("댓글 게시 성공", 200);
                }else {
                    throw new IllegalArgumentException("본인이 작성한 글이 아닙니다");
                }
            }
        }
        throw new IllegalArgumentException("해당 토큰이 유효하지 않습니다.");
    }

    public statusCodeResponseDto deleteReply(Long id, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(()-> new IllegalArgumentException("사용자 정보를 찾을 수 없다. "));
                Reply reply= replyRepository.findById(id).orElseThrow(()-> new NullPointerException("해당 댓글을 찾을 수 없습니다."));
                if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(reply.getUser().getUsername())){
                    replyRepository.deleteById(id);
                    return new statusCodeResponseDto("댓글 삭제 성공", 200);
                }else {
                    throw new IllegalArgumentException("본인이 작성한 글이 아닙니다");
                }
            }
        }
        throw  new IllegalArgumentException("토큰이 유효하지 않습니다.");
    }

}