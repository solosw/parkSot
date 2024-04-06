package com.example.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parking.pojo.Records;
import com.example.parking.pojo.form.RecordsForm;

import java.util.List;
import java.util.Map;

public interface RecordsMapper extends BaseMapper<Records> {

   List<Records> searchRecords( RecordsForm params);
}
