package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.SignUpRequestDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signup") // Login과 SignUp = Account 계정 관련 !
    public statusCodeResponseDto signup(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
        return signUpService.signUp(signupRequestDto);
    }
}