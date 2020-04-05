package com.sww.controller;

import com.sww.exception.BadRequestException;
import com.sww.pojo.ResponseBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author sww
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseBean badRequest(BadRequestException exception) {
        return new ResponseBean(exception.getMessage(), null, 0);
    }
}
