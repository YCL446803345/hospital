package com.woniu.controller;

import com.woniu.entity.Perms;
import com.woniu.service.PermsService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

    //    通过账户查询该用户所有的权限
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

}
