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
//处方表
public class Prescription {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    private Integer prescriptionStatus;

    private String spare1;

    private String spare2;

    private String spare3;

}