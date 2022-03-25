package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//医嘱类型表
public class MedicalAdviceCategory {
    private Integer id;

    private String adviceCategory;

    private String spare1;

    private String spare2;

    private String spare3;


}