package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ParkSlot {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Double lng,lat,price,extraPrice;
    private Integer slotNumber;
    private Integer useNumber;
    private String address;
}
