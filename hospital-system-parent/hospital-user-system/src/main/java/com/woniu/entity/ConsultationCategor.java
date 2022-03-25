package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//会诊类别表
public class ConsultationCategor {
    private Integer id;

    private String type;

    private String spare3;

    private String spare2;

    private String spare1;


}