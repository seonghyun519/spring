package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.SignUpRequestDto;
import com.sparta.boardhanghae.dto.StatusCodeResponseDto;
import com.sparta.boardhanghae.service.SignUpService;
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
    public StatusCodeResponseDto signup(@RequestBody @Valid SignUpRequestDto signupRequestDto) {
        return signUpService.signUp(signupRequestDto);
    }
}