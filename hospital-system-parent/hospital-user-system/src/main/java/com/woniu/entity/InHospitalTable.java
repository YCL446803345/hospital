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
//预约住院申请表
public class InHospitalTable {
    private Integer id;

    private String patientName;

    private String patientSex;

    private Integer patientAge;

    private String cardId;

    private String telephone;

    private Integer deptId;

    private Integer badId;

    private String reason;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date inHosptialTime;

    private String status;

    //模糊时间查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;

    //模糊时间查询结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;

    //外键部门名
    private String deptName;

    private String spare1;

    private String spare2;

    private String spare3;


}