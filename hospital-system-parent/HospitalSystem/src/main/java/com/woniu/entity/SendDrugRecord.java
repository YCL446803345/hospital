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
public class SendDrugRecord {

    private Integer id;
    private Integer prescriptionId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sendDrugTime;
    private String spare1;
    private String spare2;
    private String spare3;

    private String doctorName;
    private String preName;
    private String sdTime;
    private Integer pid;

}