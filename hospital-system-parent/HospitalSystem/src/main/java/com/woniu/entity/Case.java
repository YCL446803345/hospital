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
//病例表
public class Case {
    private Integer id;

    private Integer adviceId;

    private String caseDescription;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private Integer patientId;
    private Integer doctorId;
    private String patientName;
    private String doctorName;
    private String gender;
    private String no;
    private String deptName;
    private String phone;
    private String adviceDescription;
    private String bedCode;

    private String spare1;

    private String spare2;

    private String spare3;


}