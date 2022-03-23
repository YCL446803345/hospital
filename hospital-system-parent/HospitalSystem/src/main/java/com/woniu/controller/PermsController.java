package com.woniu.controller;

import com.woniu.entity.Perms;
import com.woniu.service.PermsService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 */
@RestController
public class PermsController {


    @Autowired
    private PermsService permsService;

    //通过账户查询该用户所有的权限
    @GetMapping("perms/findMenuPerms")
    public ResponseResult<List<Perms>> findAllPermsByAccount(String account){
        List<Perms> allPerms = permsService.byNamefindPerm(account);

        //存放菜单栏权限
        List<Perms> parentNode = new ArrayList<>();
        for (Perms perms : allPerms) {
            //判断是否是菜单栏根节点
            if (perms.getParentId() == null){
                parentNode.add(perms);
            }
        }

        //迭代根节点,判断是否有children
        for (Perms parent : parentNode) {
            //临时存放子节点
            List<Perms> children = new ArrayList<>();
            for (Perms perms : allPerms) {

                //判断当前根节点是否是当前所迭代的节点的父节点
                if (parent.getId() == perms.getParentId()){
                    children.add(perms);
                }
            }
            //添加到子节点集合
            parent.setChildren(children);
        }
        return new ResponseResult<List<Perms>>(parentNode,"OK",200);
    }



    // 通过id查询该用户所有的权限
    @GetMapping("user/permsByUserid")
    public ResponseResult<List<String>> getPermByUserId(Integer id){
        List<String> permsList=permsService.getPermByUserId(id);

        return new ResponseResult<List<String>>(permsList,"OK",200);
    }

    //修改该用户权限
    @RequestMapping("user/updateUserPerms")
    public ResponseResult<String> updateUserPermsById(Integer userId,String permsId){
        permsService.updatePerms(userId,permsId);

        return new ResponseResult<String>(200,"修改成功");
    }

    //获取所有权限
    @GetMapping("perms/all")
    public ResponseResult<List<Perms>> findMenuPermsAll() {
        List<Perms> allPerms = permsService.findAllPerms();

        //用于存放根节点
        List<Perms> parentNode = new ArrayList<>();
        for(Perms perms : allPerms){
            if(perms.getParentId()==null){
                parentNode.add(perms);
            }
        }

        //迭代每个根节点
        for(Perms parent : parentNode){
            parent.setChildren(getNodesChildren(parent,allPerms));
        }
        return new ResponseResult<List<Perms>>(parentNode,"OK",200);
    }

    //递归实现
    private List<Perms> getNodesChildren(Perms parent, List<Perms> allPerms) {
        List<Perms> children = new ArrayList<>();
        for(Perms item : allPerms){
            if(item.getParentId()==parent.getId()){
                children.add(item);
                //继续为当前item子节点,查找并设置它的children子节点
                item.setChildren(getNodesChildren(item,allPerms));
            }
        }
        if(children.size()==0){
            return null;
        }
        return children;
    }
}
