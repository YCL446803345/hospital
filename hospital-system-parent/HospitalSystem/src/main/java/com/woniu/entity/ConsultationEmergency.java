package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//会诊紧急情况表
public class ConsultationEmergency {
    private Integer id;

    private String status;

    private String spare3;

    private String spare2;

    private String spare1;


}