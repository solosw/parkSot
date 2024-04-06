package com.example.parking.pojo.form;

import lombok.Data;

@Data
public class RecordsForm {

    private Integer status,pageSize,start;
    private Long id;

    private String openId;
}
