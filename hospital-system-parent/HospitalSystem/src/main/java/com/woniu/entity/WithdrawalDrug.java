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
public class WithdrawalDrug {

    private Integer id;
    private Integer drugOutId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date outDrugTime;
    private String outName;
    private String spare1;
    private String spare2;
    private String spare3;

    private String doctorName;
    private String preName;
    private String outTime;
    private String outCause;

}