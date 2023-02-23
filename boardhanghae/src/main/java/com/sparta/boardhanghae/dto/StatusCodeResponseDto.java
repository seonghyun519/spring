package com.sparta.boardhanghae.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusCodeResponseDto<T> {
    private int statusCode;
    private T result;

    public StatusCodeResponseDto(int statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }
    public static <T> StatusCodeResponseDto<T> ok(T ressult){
        return new StatusCodeResponseDto<>(200, ressult);
    }
    public static <T> StatusCodeResponseDto<T> fail(int statusCode, T result){
        return new StatusCodeResponseDto<>(statusCode, result);
    }
}
