package com.woniu.service;

import com.woniu.entity.Project;

import java.util.List;

public interface ProjectService {
    //查询所有项目
    List<Project> findProjectList();
}
