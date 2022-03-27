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
//医嘱表
public class MedicalAdvice {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    private Integer adviceCategory;

    private Integer projectId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private String adviceDescription;

    private Integer adviceStatus;

    //外键角色名
    private String doctorName;
    //外键病人名
    private String patientName;
    //外键医嘱类别名
    private String adviceCategoryName;
    //外键科目名
    private String projectName;

    //模糊时间查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;

    //模糊时间查询结束时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;

    private String spare1;

    private String spare2;

    private String spare3;

    private Integer projectType;

}