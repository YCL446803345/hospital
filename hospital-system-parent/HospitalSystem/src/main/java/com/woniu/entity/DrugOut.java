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
//退药申请表
public class DrugOut {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    private Integer prescriptionId;

    private Integer outStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String outCause;

    private Integer outNum;

    private String patientName;

    private Integer num;
    private String drugName;
    private String drugType;
    private String specifications;
    private Integer drugId;
    private Integer drugOutId;
    //模糊时间查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endDate;
    private String no;

    private String spare1;

    private String spare2;

    private String spare3;

    private String doctorName;
    private String preName;
    private String cTime;
    private String oCause;
    private String oStatus;
    private Integer did;
    private Integer dstock;
    private Integer dnum;
}