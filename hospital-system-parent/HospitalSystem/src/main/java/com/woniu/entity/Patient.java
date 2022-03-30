package com.woniu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
//患者表
public class Patient {
    private Integer id;

    private String name;

    private String no;

    private Float balance;

    private Integer age;

    private String cardId;

    private String phone;

    private String gender;

    private Integer deptId;

    private Integer bedId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date appointmenttTime;

    private String baseDesc;

    private Integer doctorId;

    private Integer nurseId;

    private String status;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    //外键部门名
    private String deptName;
    //外键床位编号
    private String bedCode;
    //外键医生名
    private String doctorName;
    //外键护士名
    private String nurseName;

    private Integer medicalAdviceNeedForCheck;

    private Integer prescriptionNeedForCheck;



    //模糊时间查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startDate;

    //模糊时间查询结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endDate;

    private String spare1;

    private String spare3;

    private String spare2;


}