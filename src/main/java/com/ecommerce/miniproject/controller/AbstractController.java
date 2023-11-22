package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    protected <T> ResponseEntity<ResponseObject> sendResponse(T data, HttpStatus httpStatus, boolean success) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(data);
        responseObject.setStatus(httpStatus);
        responseObject.setSuccess(success);
        return new ResponseEntity<>(responseObject, httpStatus);
    }

    protected <T> ResponseEntity<ResponseObject> sendSuccessResponse(T data) {
        return sendResponse(data, HttpStatus.OK, true);
    }

    protected <T> ResponseEntity<ResponseObject> sendCreatedResponse(T data) {
        return sendResponse(data, HttpStatus.CREATED, true);
    }

    protected <T> ResponseEntity<ResponseObject> sendFoundResponse(T data) {
        return sendResponse(data, HttpStatus.FOUND, true);
    }
}
