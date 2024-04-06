package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.nio.file.LinkOption;

@Data
public class Records {

  private Long id;
  private String time,openId,endTime,startTime;
  private Integer howLong,status,slotId,carSlotId;
  private Double price;

  public String getStrId() {
    return id.toString();
  }

  public void setStrId(String strId) {
    this.strId = strId;
  }

  @TableField(exist = false)
  private String strId;


}
