package com.example.parking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parking.pojo.Users;
import org.apache.ibatis.annotations.Update;

public interface UsersMapper extends BaseMapper<Users> {

    @Update("update users set money=money-#{money} where open_id=#{id}")
    void changeMoney(Users users);
}
