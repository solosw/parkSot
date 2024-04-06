package com.example.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parking.pojo.ParkSlot;
import org.apache.ibatis.annotations.Update;

public interface ParkSlotMapper extends BaseMapper<ParkSlot> {

    @Update("update park_slot set use_number=use_number+#{count} where id=#{id}")
    void updateUseNUmber(Integer count,Integer id);
}
