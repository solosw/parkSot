package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Messages {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String content,openId;
    private LocalDateTime time;
    private Integer status;
    @TableField(exist = false)
    private Integer type;
    @TableField(exist = false)
    private Long messageId;
}
