package com.example.parking.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.parking.mapper.*;
import com.example.parking.pojo.*;
import com.example.parking.pojo.form.Admin;
import com.example.parking.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    ParkSlotMapper parkSlotMapper;
    @Autowired
    RecordsMapper recordsMapper;
    @Autowired
    DenunciationMapper denunciationMapper;
    @Autowired
    MessagesMapper messagesMapper;
    @Autowired
    FeedbackMapper feedbackMapper;
    @RequestMapping("/login")
    public Users login(@RequestBody Users users){


        System.out.println(users.toString());
        String token=null;
        if(users.getToken()==null){
            Map<String,String> newMap=new HashMap<>();
            newMap.put("openId",users.getId());
            token=jwtUtils.getToken(newMap);
            if(usersMapper.selectById(users.getId())==null){
                usersMapper.insert(users);

            }
        }else {

            DecodedJWT decodedJWT=jwtUtils.verify(users.getToken());
            if(decodedJWT!=null){

                return usersMapper.selectById(users.getId());

            }else
            {
                Map<String,String> newMap=new HashMap<>();
                newMap.put("openId",users.getId());
                token=jwtUtils.getToken(newMap);
                if(usersMapper.selectById(users.getId())==null){
                    usersMapper.insert(users);

                }

            }


        }
        if(users.getToken()==null) users.setToken(token);
        return users;
    }


    @RequestMapping("/isLogin")
    public Users isLogin(@RequestBody Users users){
        return usersMapper.selectById(users.getId());

    }

    @RequestMapping("/getMyinfo")
    public Users getMyinfo(@RequestBody Users users){
        return usersMapper.selectById(users.getId());

    }
    @RequestMapping("/updateInfo")
    public void updateInfo(@RequestBody Users users){
         usersMapper.updateById(users);

    }
    @RequestMapping("/getSlots")
    public List<ParkSlot> getSlots(){
        return parkSlotMapper.selectList(null);
    }

    @RequestMapping("/appointSlot")
    public Users appointSlot(@RequestBody Records records){

        recordsMapper.insert(records);
        Users users=new Users();
        users.setId(records.getOpenId());
        users.setMoney(records.getPrice());
       // usersMapper.changeMoney(users);
        parkSlotMapper.updateUseNUmber(1,records.getSlotId());
        return usersMapper.selectById(records.getOpenId());
    }

    @RequestMapping("/addDenunciation")
    public void addDenunciation(@RequestBody Denunciation denunciation){

        denunciationMapper.insert(denunciation);

    }
    @RequestMapping("/addFeedback")
    public void addFeedback(@RequestBody Feedback feedback){

        feedbackMapper.insert(feedback);

    }

    @RequestMapping("/getMyRecords")
    public List<Records> getMyRecords(@RequestBody Records records){
        List<Records>  recordsList=recordsMapper.selectList(new QueryWrapper<Records>().eq("open_id",records.getOpenId()).in("status",0,1));
        System.out.println(recordsList);
        return recordsList;

    }
    @RequestMapping("/cancelAppoint")
    public void cancelAppoint(@RequestBody Records records){

        recordsMapper.deleteById(records.getId());

    }
    @RequestMapping("/startAppoint")
    public void startAppoint(@RequestBody Records records){
        // 创建 SimpleDateFormat 对象，指定日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        Date now = new Date();

        // 格式化当前时间
        String formattedDate = sdf.format(now);
        records.setStatus(1);
        records.setStartTime(formattedDate);
        recordsMapper.updateById(records);

    }
    @RequestMapping("/endAppoint")
    public void endAppoint(@RequestBody Records records){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        Date now = new Date();

        // 格式化当前时间
        String formattedDate = sdf.format(now);
        records.setStatus(2);
        records.setEndTime(formattedDate);
        parkSlotMapper.updateUseNUmber(-1,records.getSlotId());
        recordsMapper.updateById(records);

    }

    @RequestMapping("/getMyOrder")
    public List<Records> getMyOrder(@RequestBody Records records){
        List<Records>  recordsList=recordsMapper.selectList(new QueryWrapper<Records>().eq("open_id",records.getOpenId()).in("status",2,3));
        System.out.println(recordsList);
        return recordsList;

    }


    @RequestMapping("/payOrder")
    public Users payOrder(@RequestBody Records records){
        Users users=new Users();
        users.setMoney(records.getPrice());
        users.setId(records.getOpenId());
        String id=records.getOpenId();
        usersMapper.changeMoney(users);
        records.setPrice(null);
        records.setOpenId(null);
        records.setStatus(3);
        recordsMapper.updateById(records);
        return usersMapper.selectById(id);
    }


    @RequestMapping("/deleteOrder")
    public void deleteOrder(@RequestBody Records records){
        recordsMapper.deleteById(records.getId());
    }

    @RequestMapping("/getMyMessages")
    public List<Messages> getMyMessages(@RequestBody Messages messages){
        return messagesMapper.selectList(new QueryWrapper<Messages>().eq("open_id",messages.getOpenId()));
    }

    @RequestMapping("/lookMessage")
    public void lookMessage(@RequestBody Messages messages){

        messages.setStatus(1);
        messagesMapper.updateById(messages);
    }

    @RequestMapping("/deleteMessage")
    public void deleteMessage(@RequestBody Messages messages){


        messagesMapper.deleteById(messages.getId());
    }
}
