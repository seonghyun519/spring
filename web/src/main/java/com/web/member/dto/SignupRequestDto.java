package com.web.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "Member ID는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{3,11}$", message = "ID는 영문으로 시작하며, 4~12자의 영문자 또는 숫자로 이루어져야 합니다.")
    private String memberId;

    @NotBlank(message = "Password는 필수 입력 항목입니다.")
    @Size(min = 8, message = "Password는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "Password는 영문 대소문자, 숫자, 특수문자($@$!%*#?&) 중 1개 이상을 포함해야 합니다.")
    private String memberPassword;
}

