package com.woniu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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


    private String spare1;

    private String spare2;

    private String spare3;


    private String roleName;

    private String deptName;

    private String shift;

    private Integer schedulingId;

    //排班时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date time;


}