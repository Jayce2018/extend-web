package com.jayce.DTO;

import lombok.Data;

@Data
public class ReturnDTO {
    private Integer code;

    private String message;

    private Object result;

}
