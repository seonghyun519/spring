package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignUpRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp="^[a-z0-9]+$")
    private String username;
    @Size(min=8, max=15)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String pwd;
    private boolean admin = false;
    private String adminToken = "";
}