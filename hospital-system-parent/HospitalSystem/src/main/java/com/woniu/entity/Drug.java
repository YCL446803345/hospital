package com.woniu.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {

    private Integer id;
    private String name;
    private String drugType;
    private Float makePrice;
    private Float salePrice;
    private Integer stock;
    private String specifications;
    private String status;
    private String spare1;
    private String spare2;
    private String spare3;
    private Integer inNum;
    private Integer outNum;

}