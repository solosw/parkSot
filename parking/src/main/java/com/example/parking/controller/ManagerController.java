package com.example.parking.controller;

import com.alibaba.fastjson.JSON;
import com.example.parking.mapper.*;
import com.example.parking.pojo.*;
import com.example.parking.pojo.form.Admin;
import com.example.parking.pojo.form.RecordsForm;
import com.example.parking.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ManagerController {

    Admin admin;
    @Autowired
    DenunciationMapper denunciationMapper;
    @Autowired
    FileUtils fileUtils;

    @Autowired
    TipsMapper tipsMapper;

    @Autowired
    ParkSlotMapper parkSlotMapper;

    @Autowired
    RecordsMapper recordsMapper;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    MessagesMapper messagesMapper;

    @PostConstruct
    public void getManager(){

        String json=fileUtils.readJsonFile("admin.json");
        admin= JSON.parseObject(json, Admin.class);
        System.out.println(admin.toString());
    }
    @RequestMapping("/getSystem")
    public Admin getSystem(){
        return admin;
    }
    @RequestMapping("/managerLogin")
    public Admin login(@RequestBody Admin admin){
        System.out.println(admin.toString());
        if(this.admin.getPassword().equals(admin.getPassword())&&this.admin.getUsername().equals(admin.getUsername())){
            return this.admin;
        }else
        {
            return null;
        }
    }
    @RequestMapping("/updateManager")
    public Admin updateManager(@RequestBody Admin admin){

        System.out.println(admin.toString());
        this.admin=admin;
        return admin;
    }
    @RequestMapping("/deleteKTips")
    public void deleteKTips(@RequestBody Tips id){
       tipsMapper.deleteById(id.getId());
    }

    @RequestMapping("/addTips")
    public void addTips(@RequestBody Tips tips){
        tipsMapper.insert(tips);
    }
    @RequestMapping("/getTips")
    public List<Tips> getTips(){
       return tipsMapper.selectList(null);
    }
    @RequestMapping("/addSlot")
    public void addSlot(@RequestBody ParkSlot slot){

        System.out.println(slot.toString());

        parkSlotMapper.insert(slot);
    }
    @RequestMapping("/getSlots")
    public List<ParkSlot> getSlots(){
        return parkSlotMapper.selectList(null);
    }

    @RequestMapping("/deleteSlot")
    public void deleteSlot(@RequestBody ParkSlot parkSlot){

         parkSlotMapper.deleteById(parkSlot.getId());
    }

    @RequestMapping("/updateSlot")
    public void updateSlot(@RequestBody ParkSlot id){
        parkSlotMapper.updateById(id);
    }
    @RequestMapping("/getBaseInfo")
    public Admin getBaseInfo(){

        Admin admin=new Admin();
        admin.setVipDiscount(this.admin.getVipDiscount());
        admin.setAntecedentDay(this.admin.getAntecedentDay());
        return admin;
    }

    @RequestMapping("/getOrderByPage")
    public List<Records> getOrderByPage(@RequestBody RecordsForm form){


        return recordsMapper.searchRecords(form);
    }


    @RequestMapping("/getOrderCount")
    public Integer getOrderCount(){


        return recordsMapper.selectCount(null);
    }

    @RequestMapping("/deleteOrder")
    public void deleteOrder(@RequestBody Records records){

         recordsMapper.deleteById(records.getId());
    }


    @RequestMapping("/getFeedByPage")
    public List<Feedback> getFeedByPage(@RequestBody RecordsForm form){


        return feedbackMapper.searchFeedback(form);
    }


    @RequestMapping("/getFeedCount")
    public Integer getFeedCount(){


        return feedbackMapper.selectCount(null);
    }

    @RequestMapping("/deleteFeed")
    public void deleteFeed(@RequestBody Feedback feedback){

        feedbackMapper.deleteById(feedback.getId());
    }


    @RequestMapping("/addMessage")
    public void addMessage(@RequestBody Messages feedback){

        if(feedback.getType()==0){
            Feedback feedback1=new Feedback();
            feedback1.setStatus(1);
            feedback1.setId(feedback.getMessageId());
            feedbackMapper.updateById(feedback1);
        }else
        {
            Denunciation feedback1=new Denunciation();
            feedback1.setStatus(1);
            feedback1.setId(feedback.getMessageId());
            denunciationMapper.updateById(feedback1);

        }
       messagesMapper.insert(feedback);
    }

    @RequestMapping("/getDenunciationByPage")
    public List<Denunciation> getDenunciationByPage(@RequestBody RecordsForm form){


        return denunciationMapper.searchDenunciation(form);
    }


    @RequestMapping("/getDenunciationCount")
    public Integer getDenunciationCount(){


        return denunciationMapper.selectCount(null);
    }

    @RequestMapping("/deleteDenunciation")
    public void deleteDenunciation(@RequestBody Denunciation feedback){

        denunciationMapper.deleteById(feedback.getId());
    }

}
