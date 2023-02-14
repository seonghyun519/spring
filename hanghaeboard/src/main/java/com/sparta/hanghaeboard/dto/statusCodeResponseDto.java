package com.sparta.hanghaeboard.dto;

import lombok.Getter;
@Getter
public class statusCodeResponseDto {
    private String msg;
    private int statusCode;

    public statusCodeResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
