package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;

import com.woniu.service.DrugService;
import com.woniu.util.FileUtils;
import com.woniu.util.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 药品信息
 */
@RestController
public class DrugInfoController {

    @Autowired
    private DrugService drugService;


    //查询所有上架药品信息
    @GetMapping("drug/findAllDrug")
    public ResponseResult<PageInfo<Drug>> findAllDrug(Drug drug,
                                                  @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                  @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Drug> drugList = drugService.findAllDrug(drug);
        PageInfo drugPageInfo = new PageInfo(drugList);
        return new ResponseResult<PageInfo<Drug>>(drugPageInfo,"OK",200);
    }

    //查询所有下架药品信息
    @GetMapping("drug/findAllDownDrug")
    public ResponseResult<PageInfo<Drug>> findAllDownDrug(Drug drug,
                                                      @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                      @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Drug> drugList = drugService.findAllDownDrug(drug);
        PageInfo drugPageInfo = new PageInfo(drugList);
        return new ResponseResult<PageInfo<Drug>>(drugPageInfo,"OK",200);
    }

    //查询所有药品类别
    @GetMapping("drug/findAllDrugType")
    public ResponseResult<LinkedHashSet<String>> findAllDrugType(){
        List<String> types = drugService.finAllType();
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(types);
        return new ResponseResult<LinkedHashSet<String>>(hashSet,"OK",200);
    }

    //修改药品信息
    @PostMapping("drug/updateDrugStatus")
    public ResponseResult updateDrugStatus(Drug drug){
        drug.setStatus("0");
        drugService.updateDrug(drug);
        return ResponseResult.ok();
    }

    //添加药品,附带药品图片上传
    @PostMapping("drug/addDrug")
    public ResponseResult addDrug(Drug drug, MultipartFile file){
        String url = FileUtils.uploadFile(file);
        drug.setSpare3(url);
        drugService.addDrug(drug);
        return ResponseResult.ok();
    }

    //by药品id查询药品信息
    @GetMapping("drug/findDrugById")
    public ResponseResult<Drug> getDrugById(Integer id){
        Drug drug = drugService.getDrugById(id);
        return new ResponseResult<Drug>(drug,"OK",200);
    }

    //测试图片上传
    @PostMapping("teacherAvatarUplod")
    public ResponseResult<String> avaUpload(MultipartFile file){
        String url = FileUtils.uploadFile(file);
        System.out.println(url);
        return new ResponseResult<String>(url,"上传OK",200);
    }

    //修改药品整体信息
    @PostMapping("drug/updateDrugInfo")
    public ResponseResult updateDrugInfo(@RequestBody Drug updateDrug){
        drugService.update(updateDrug);
        return ResponseResult.ok();
    }

    //支持批量上架
    @PostMapping("drug/batchDown")
    public ResponseResult batchDown(String idStrs){
        List<String> strings = Arrays.asList(idStrs.split(","));
        drugService.updateByIdBatchStatus(strings);
        return ResponseResult.ok();
    }
}
