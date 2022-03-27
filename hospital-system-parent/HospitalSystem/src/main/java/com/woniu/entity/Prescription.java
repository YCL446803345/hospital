package com.woniu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
//处方表
public class Prescription {

    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer prescriptionStatus;

    private String spare1;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String spare2;

    private String spare3;

    private String drugs;

    private Date startTime;

    private Date endTime;


    //药房查看处方表所需字段
    private Integer pid;
    private String doctorName;
    private String nurseName;
    private String paName;
    private Integer pdNum;
    private String drugName;
    private Integer stock;
    private Integer num;
}