package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Denunciation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content,openId;
    private Integer slotId,status;
}
