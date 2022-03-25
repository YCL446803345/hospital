package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perms {

    private Integer id;
    private String name;
    private String pagePerm;
    private String pageLink;
    private Integer parentId;
    private String type;
    private String percode;
    private String spare1;
    private String spare2;
    private String spare3;
    private List<Perms> children;  //子菜单

}