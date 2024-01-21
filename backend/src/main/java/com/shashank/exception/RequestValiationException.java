package com.shashank.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestValiationException extends RuntimeException {


    public RequestValiationException(String message) {
        super(message);
    }
}
