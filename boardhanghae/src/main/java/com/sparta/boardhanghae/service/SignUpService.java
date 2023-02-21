package com.sparta.boardhanghae.service;

import com.sparta.boardhanghae.dto.SignUpRequestDto;
import com.sparta.boardhanghae.dto.statusCodeResponseDto;
import com.sparta.boardhanghae.entity.User;
import com.sparta.boardhanghae.entity.UserRoleEnum;
import com.sparta.boardhanghae.jwt.JwtUtil;
import com.sparta.boardhanghae.repository.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final PasswordEncoder passwordEncoder;
    private final SignUpRepository signUpRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public statusCodeResponseDto signUp(SignUpRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String pwd = passwordEncoder.encode(signupRequestDto.getPwd());

        // 회원 중복 확인
        Optional<User> found = signUpRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
//        String email = signupRequestDto.getEmail();
        // 사용자 ROLE 확인 //정리 필요
        UserRoleEnum role = UserRoleEnum.USER;
        if (!signupRequestDto.getAdminToken().isEmpty()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            } else{
                role = UserRoleEnum.ADMIN;
            }
        }
        User user = new User(username, pwd, role);
        signUpRepository.save(user);
        return new statusCodeResponseDto("회원가입 완료", 200);
    }

//    public boolean roleChek(SignUpRequestDto dto) { 이전에도 사용안함 체크 필요
//        if (dto.getAdminToken().equals(jwtSecretKey)){
//            return true;
//        }else {
//            return false;
//        }
//    }
}
