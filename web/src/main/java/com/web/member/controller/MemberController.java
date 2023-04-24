package com.web.member.controller;

import com.web.member.dto.SignupRequestDto;
import com.web.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "ID는 영문으로 시작하며, 4~12자의 영문자 또는 숫자로 이루어져야 합니다./ Password는 영문 대소문자, 숫자, 특수문자($@$!%*#?&) 중 1개 이상을 포함해야 합니다.")
    @PostMapping("/signup")
    public String signup(@Validated @RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @GetMapping("/login")
    public String login(String memberId, String memberPassword){
        return memberService.login(memberId, memberPassword);
    }
}