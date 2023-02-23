package com.sparta.boardhanghae.exceptionHandler;

import com.sparta.boardhanghae.dto.StatusCodeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.function.EntityResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

//  <?>대신 <String> 확인하고 사용
    @ExceptionHandler(Exception.class)
    public StatusCodeResponseDto<?> handlerExcepiton(Exception e){
        return StatusCodeResponseDto.fail(500, e.getMessage());
    }
//    <String>일 경우 체크
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<StatusCodeResponseDto<?>> handlefirst(Exception e) {
        return new ResponseEntity<>(StatusCodeResponseDto.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StatusCodeResponseDto<?>> handlet(Exception e) {
        return new ResponseEntity<>(StatusCodeResponseDto.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StatusCodeResponseDto<?>> handletwo(Exception e) {
        return new ResponseEntity<>(StatusCodeResponseDto.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StatusCodeResponseDto<?>> handlethree(Exception e) {
        return new ResponseEntity<>(StatusCodeResponseDto.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
