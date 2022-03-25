package com.woniu.controller;

import com.woniu.entity.User;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;



//    //职工列表
//    @RequestMapping("worker/list")
////    @PreAuthorize("hasAnyAuthority('worker:list')")
//    public ResponseEntity<PageInfo<Worker>> findWorkerList(String account, Integer roleId,Integer deptId,
//                                                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
//                                                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
//        PageHelper.startPage(pageNum,pageSize);
//        List<Worker> workerList = workerService.findWorkerList(account,roleId,deptId);
//        System.out.println("Controller+"+workerList);
//        PageInfo pageInfo = new PageInfo(workerList);
//        return new ResponseEntity<PageInfo<Worker>>(pageInfo, HttpStatus.OK);
//    }



    //添加职工
    @RequestMapping("user/add")
//    @PreAuthorize("hasAnyAuthority('worker:add')")
    public ResponseResult<String> addWorker(@RequestBody User user){
        userService.addUser(user);
        return new ResponseResult<String>(200,"添加成功");
    }
//
//    //删除职工
//    @RequestMapping("worker/delete")
////    @PreAuthorize("hasAnyAuthority('worker:delete')")
//    public ResponseResult<String> deleteWorker(Integer id){
//
//        if (StringUtils.isEmpty(id)){
//            return new ResponseResult<String>(400,"id为空");
//        }
//        workerService.deleteWorkerById(id);
//        return new ResponseResult<String>(200,"删除成功");
//    }
//
//    //修改职工
//    @RequestMapping("worker/update")
////    @PreAuthorize("hasAnyAuthority('worker:update')")
//    public ResponseResult<String> updateWorker(@RequestBody Worker worker){
//        workerService.updateWorker(worker);
//        return new ResponseResult<String>(200,"修改成功");
//    }
}
