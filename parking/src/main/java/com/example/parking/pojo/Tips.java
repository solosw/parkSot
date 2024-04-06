package com.example.parking.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Tips {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title,content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;

}
