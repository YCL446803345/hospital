package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//处方药品中间表
public class PrescriptionDrug {
    private Integer prescriptionId;

    private Integer drugId;

    private Integer num;

    private String spare1;

    private String spare2;

    private String spare3;

}