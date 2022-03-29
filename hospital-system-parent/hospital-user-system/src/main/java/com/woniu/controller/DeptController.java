package com.woniu.controller;

import com.woniu.entity.Dept;
import com.woniu.service.DeptService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    //查询角色
    @RequestMapping("/dept/list")
    public ResponseResult<List<Dept>> findDeptList(){
        List<Dept> deptList = deptService.findDeptList();
        return new ResponseResult<List<Dept>>(deptList,"OK",200);
    }

}
