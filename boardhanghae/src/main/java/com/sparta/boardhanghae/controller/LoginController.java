package com.sparta.boardhanghae.controller;

import com.sparta.boardhanghae.dto.LoginRequestDto;
import com.sparta.boardhanghae.dto.StatusCodeResponseDto;
import com.sparta.boardhanghae.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/login")
    public StatusCodeResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}