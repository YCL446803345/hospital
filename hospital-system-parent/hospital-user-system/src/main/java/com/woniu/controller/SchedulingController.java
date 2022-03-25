package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Scheduling;
import com.woniu.service.SchedulingService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 排班管理
 */
@RestController
public class SchedulingController {
    @Autowired
    private SchedulingService schedulingService;


    //排班列表
    @RequestMapping("scheduling/list")
//    @PreAuthorize("hasAnyAuthority('scheduling:list')")
    public ResponseEntity<PageInfo<Scheduling>> findSchedulingList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Scheduling> schedulingList = schedulingService.findSchedulingList();
        System.out.println("Controller+" + schedulingList);
        PageInfo pageInfo = new PageInfo(schedulingList);
        return new ResponseEntity<PageInfo<Scheduling>>(pageInfo, HttpStatus.OK);
    }

    //修改排班
    @RequestMapping("scheduling/update")
//    @PreAuthorize("hasAnyAuthority('scheduling:update')")
    public ResponseResult<String> updateScheduling(@RequestBody Scheduling scheduling){
        schedulingService.updateScheduling(scheduling);
        return new ResponseResult<String>(200,"修改成功");
    }

}