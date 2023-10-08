package com.ecommerce.miniproject.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class Exceptions extends RuntimeException {
    private HttpStatus httpStatusCode;

    public Exceptions(String message, HttpStatus httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String toString() {
        return "Exception occurred was with status code" + httpStatusCode;
    }
}
