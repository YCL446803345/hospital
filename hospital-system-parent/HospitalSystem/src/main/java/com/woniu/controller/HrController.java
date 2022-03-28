package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Worker;
import com.woniu.service.WorkerService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人事管理
 */
@RestController
public class HrController {
    @Autowired
    private WorkerService workerService;



    //职工列表
    @RequestMapping("worker/list")
//    @PreAuthorize("hasAnyAuthority('worker:list')")
    public ResponseEntity<PageInfo<Worker>> findWorkerList(Worker worker,
                                                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Worker> workerList = workerService.findWorkerList(worker);

        PageInfo pageInfo = new PageInfo(workerList);
        return new ResponseEntity<PageInfo<Worker>>(pageInfo, HttpStatus.OK);
    }



    //添加职工
    @PostMapping("worker/add")
//    @PreAuthorize("hasAnyAuthority('worker:add')")
    public ResponseResult<String> addWorker(@RequestBody Worker worker){
        workerService.addWorker(worker);
        return new ResponseResult<String>(200,"添加成功");
    }

    //删除职工
    @RequestMapping("worker/delete")
//    @PreAuthorize("hasAnyAuthority('worker:delete')")
    public ResponseResult<String> deleteWorker(Integer id){

        if (StringUtils.isEmpty(id)){
            return new ResponseResult<String>(400,"id为空");
        }
        workerService.deleteWorkerById(id);
        return new ResponseResult<String>(200,"删除成功");
    }

    //修改职工
    @RequestMapping("worker/update")
//    @PreAuthorize("hasAnyAuthority('worker:update')")
    public ResponseResult<String> updateWorker(@RequestBody Worker worker){
        workerService.updateWorker(worker);
        return new ResponseResult<String>(200,"修改成功");
    }
}
