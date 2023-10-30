package com.ecommerce.miniproject.dto;

import lombok.Data;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;

@Data
@ToString
public class ResponseObject {
    private Object data;
    private HttpStatus status;
}
