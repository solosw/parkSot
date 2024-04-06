package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Users {

    @TableId(value = "open_id")
    private String id;
    private String phone,name,carCard,carKind;
    @TableField(exist = false)
    private String token;
    private Integer status;
    private Double money;
}
