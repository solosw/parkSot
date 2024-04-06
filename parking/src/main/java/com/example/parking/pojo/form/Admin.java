package com.example.parking.pojo.form;

import lombok.Data;

@Data
public class Admin {

    private String password,username,wxKey,webKey,jsKey,codeSafeMsy,wxAppid,wxSecret;
    private Double vipDiscount;
    private Integer antecedentDay;
}
