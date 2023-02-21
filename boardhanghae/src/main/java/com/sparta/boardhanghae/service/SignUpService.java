package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.SignUpRequestDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.entity.User;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final SignUpRepository signUpRepository;
    private final JwtUtil jwtUtil;
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public statusCodeResponseDto signUp(SignUpRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String pwd = signupRequestDto.getPwd();

        // 회원 중복 확인
        Optional<User> found = signUpRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인 //정리 필요
        UserRoleEnum role = UserRoleEnum.USER;
        if (!signupRequestDto.getJwtSecretKey().isEmpty()) {
            if (!signupRequestDto.getJwtSecretKey().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            } else{
                role = UserRoleEnum.ADMIN;
            }
        }
        User user = new User(username, pwd, role);
        signUpRepository.save(user);
        return new statusCodeResponseDto("회원가입 완료", 200);
    }

    public boolean roleChek(SignUpRequestDto dto) {
        if (dto.getAdminToken().equals(jwtSecretKey)){
            return true;
        }else {
            return false;
        }
    }
}
