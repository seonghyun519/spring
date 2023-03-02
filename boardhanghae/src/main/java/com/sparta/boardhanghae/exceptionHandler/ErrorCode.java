package com.sparta.boardhanghae.exceptionHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    UnAuthorized(HttpStatus.UNAUTHORIZED, "로그인을 해주세요."),
    NotFoundUser(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다."),
    NotMatchUser(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다."),
    InValidException(HttpStatus.BAD_REQUEST, "값이 잘못되었습니다."),
    NotFoundPost(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
