package com.woniu.controller;

import com.woniu.entity.DrugType;
import com.woniu.service.DrugTypeService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DrugTypeController {

    @Autowired
    private DrugTypeService drugTypeService;

    //查询所有药品类别
    @GetMapping("drug/findAllType")
    public ResponseResult<List<DrugType>> findAllType(){
        List<DrugType> list = drugTypeService.findAll();
        return new ResponseResult<List<DrugType>>(list,"OK",200);
    }

    //添加药品类别
    @PostMapping("drug/addType")
    @PreAuthorize("hasAnyAuthority('drug:addtype')")
    public ResponseResult addType(@RequestBody DrugType drugType){
        drugTypeService.add(drugType);
        return ResponseResult.ok();
    }

}
