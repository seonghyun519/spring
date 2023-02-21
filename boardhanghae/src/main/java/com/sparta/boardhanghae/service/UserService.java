package com.sparta.boardhanghae.service;

import com.sparta.boardhanghae.dto.LoginRequestDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.entity.User;
import com.sparta.boardhanghae.jwt.JwtUtil;
import com.sparta.boardhanghae.repository.SignUpRepository;
import com.sparta.boardhanghae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SignUpRepository signUpRepository;
    private final JwtUtil jwtUtil; //jwtUtil 의존성 주입 //jwtUtil @Component 사용되어 빈이 등록되어 의존성 주입이됨


    @Transactional(readOnly = true)
    public statusCodeResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String pwd = loginRequestDto.getPwd();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPwd().equals(pwd)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new statusCodeResponseDto("로그인 완료", 200);
    }
}
