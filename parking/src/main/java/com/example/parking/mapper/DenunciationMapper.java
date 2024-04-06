package com.example.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parking.pojo.Denunciation;
import com.example.parking.pojo.form.RecordsForm;

import java.util.List;

public interface DenunciationMapper extends BaseMapper<Denunciation> {
    List<Denunciation> searchDenunciation(RecordsForm form);
}
