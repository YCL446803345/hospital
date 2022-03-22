package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Worker {
    private Integer id;

    private String name;

    private String account;

    private String password;

    private Integer roleId;

    private Integer deptId;

    private String gender;

    private String salary;

    private String status;

    private String roleName;

    private String deptName;

    private String spare3;


}