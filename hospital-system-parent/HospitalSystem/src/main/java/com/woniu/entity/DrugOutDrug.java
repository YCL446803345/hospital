package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//退药药品中间表
public class DrugOutDrug {
    private Integer id;
    private Integer drugId;
    private Integer drugOutId;
    private Integer num;

}
