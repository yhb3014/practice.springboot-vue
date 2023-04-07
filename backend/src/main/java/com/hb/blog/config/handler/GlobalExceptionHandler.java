package com.hb.blog.config.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hb.blog.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseDto<String> handleArgumentException(CustomException e) {

        return new ResponseDto<>(e.getErrorCode().getStatus().value(), e.getMessage());
    }
}
