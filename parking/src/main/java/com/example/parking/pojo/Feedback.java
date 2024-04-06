package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Feedback {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String openId,content;
    private Integer status;
}
