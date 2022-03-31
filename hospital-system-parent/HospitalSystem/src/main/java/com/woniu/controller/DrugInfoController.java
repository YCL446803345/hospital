package com.woniu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;

import com.woniu.service.DrugService;
import com.woniu.util.FileUtils;
import com.woniu.util.ResponseResult;
import com.woniu.util.TimeUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 药品信息
 */
@RestController
public class DrugInfoController {

    @Autowired
    private DrugService drugService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("drug/addRedisToken")
    public ResponseResult<String> getToken(){
        //生产随机数,存入redis
        Date date = new Date();
        String nowTime = TimeUtil.getNowTime(date);
        int i = new Random().nextInt(10000);
        String redisToken = nowTime+i;
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(redisToken);
        ops.set(redisToken);
        return new ResponseResult<String>(redisToken,"OK",200);
    }

    //查询所有上架药品信息
    @GetMapping("drug/findAllDrug")
    @PreAuthorize("hasAnyAuthority('medicine:list')")
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
    @PreAuthorize("hasAnyAuthority('drug:storageList')")
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

    //下架药品
    @PostMapping("drug/updateDrugStatus")
    @PreAuthorize("hasAnyAuthority('drug:down')")
    public ResponseResult<String> updateDrugStatus(@RequestParam("redisToken") String redisToken,@RequestParam("id") Integer id){
        //直接删除redisToken,删除成功代表第一次请求,否则失败
        if (redisTemplate.delete(redisToken)) {
            try {
                Drug drug = new Drug();
                drug.setId(id);
                drug.setStatus("0");
                drugService.updateDrug(drug);
                return new ResponseResult<String>(200, "OK");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseResult<String>(500,"请不要频繁点击");
    }

    //添加药品
    @PostMapping("drug/addDrug")
    @PreAuthorize("hasAnyAuthority('drug:add')")
    public ResponseResult addDrug(@RequestBody Drug drug){
        List<Drug> drug1 = drugService.byNameGetDrug(drug.getName());
        if (drug1.size() == 0){
            drug.setStatus("0");
            drugService.addDrug(drug);
        }else {
            for (Drug drug2 : drug1) {
                drugService.updateStock(drug2.getName(),drug.getStock()+drug2.getStock());
            }
        }
        return ResponseResult.ok();
    }

    //药房查看点击图片查看药品单个信息
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

    //药库修改药品整体信息
    @PostMapping("drug/updateDrugInfo")
    @PreAuthorize("hasAnyAuthority('drug:update')")
    public ResponseResult updateDrugInfo(@RequestBody Drug updateDrug){
        drugService.update(updateDrug);
        return ResponseResult.ok();
    }
    //药库批量上架
    @PostMapping("drug/batchDown")
    @PreAuthorize("hasAnyAuthority('drug:unload')")
    public ResponseResult batchDown(String idStrs){
        List<String> strings = Arrays.asList(idStrs.split(","));
        drugService.updateByIdBatchStatus(strings);
        return ResponseResult.ok();
    }

    //药库根据药品id修改库存
    @PostMapping("drug/byIdUpdateStock")
    @PreAuthorize("hasAnyAuthority('drug:addStorage')")
    public ResponseResult byIdUpdateStock(@RequestBody Drug drug){
        drugService.addDrugStockById(drug);
        return ResponseResult.ok();
    }

    //药库删除药品
    @PostMapping("drug/delete")
    @PreAuthorize("hasAnyAuthority('drug:delete')")
    public ResponseResult deleteDrugById(Integer id){
        drugService.deleteDrugById(id);
        return ResponseResult.ok();
    }

}
