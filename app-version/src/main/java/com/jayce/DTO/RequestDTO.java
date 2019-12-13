package com.jayce.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class RequestDTO {

    private Integer id;

    private String name;

    private String author;

    private Date createTime;

    private String value;


}
