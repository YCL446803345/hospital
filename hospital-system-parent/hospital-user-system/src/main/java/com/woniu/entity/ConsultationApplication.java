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
//会诊申请表
public class ConsultationApplication {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date consultationDate;

    private String reason;

    private String desc;

    private Integer consultationEmergencyId;

    private Integer consultationCategoryId;

    private String status;
    //外键医生名
    private String doctorName;
    //外键患者名
    private String patientName;
    //外键会诊紧急情况名
    private String consultationEmergencyName;
    //外键会诊类别名
    private String consultationCategoryName;
    //模糊时间查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
    //模糊时间查询结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;

    private String spare3;

    private String spare2;

    private String spare1;


}