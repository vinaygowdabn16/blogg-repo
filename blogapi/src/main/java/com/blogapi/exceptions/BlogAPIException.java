package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BlogAPIException extends Throwable {
    public BlogAPIException(Object status, String message) {
        super(message);
    }
}
