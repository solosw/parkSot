package com.example.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parking.pojo.Feedback;
import com.example.parking.pojo.Records;
import com.example.parking.pojo.form.RecordsForm;

import java.util.List;

public interface FeedbackMapper extends BaseMapper<Feedback> {
    List<Feedback> searchFeedback(RecordsForm params);
}
