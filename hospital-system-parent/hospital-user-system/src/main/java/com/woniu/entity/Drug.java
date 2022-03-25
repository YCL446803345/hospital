package com.woniu.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {

    private Integer id;
    private String name;
    private String drugType;
    private Float makePrice;
    private Float salePrice;
    private Integer stock;
    private String specifications;
    private String status;
    private String spare1;
    private String spare2;
    private String spare3;
    private Integer inNum;
    private Integer outNum;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;
    private Integer num;
    private Float money;


}