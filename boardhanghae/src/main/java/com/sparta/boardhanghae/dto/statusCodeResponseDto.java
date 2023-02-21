package com.sparta.boardhanghae.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class statusCodeResponseDto {
    private String msg;
    private int statusCode;

    public statusCodeResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
