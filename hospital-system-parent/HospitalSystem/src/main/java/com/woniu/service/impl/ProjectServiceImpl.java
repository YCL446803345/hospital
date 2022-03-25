package com.woniu.service.impl;

import com.woniu.entity.Project;
import com.woniu.entity.ProjectExample;
import com.woniu.mapper.ProjectMapper;
import com.woniu.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    //查询所有项目列表
    public List<Project> findProjectList() {
        return projectMapper.selectByExample(null);
    }
}
