package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.LoginRequestDto;
import com.sparta.hanghaeboard.dto.statusCodeResponseDto;
import com.sparta.hanghaeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LoginController {

    private final UserService userService;

//    @GetMapping("/login")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
//    }

    @ResponseBody
    @PostMapping("/login")
    public statusCodeResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) { //form태그에서 받아온 값을 ajax로 받아 @RequestBody로 변경
        return userService.login(loginRequestDto, response);
    }
}