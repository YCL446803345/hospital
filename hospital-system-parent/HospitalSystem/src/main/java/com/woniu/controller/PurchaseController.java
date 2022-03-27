package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;
import com.woniu.entity.Purchase;
import com.woniu.service.PurchaseService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    //添加采购清单
    @PostMapping("purchase/addPurchase")
    public ResponseResult addPurchase(@RequestBody Purchase purchase){
        purchaseService.addPurchase(purchase);
        return ResponseResult.ok();
    }

    //查询采购清单,默认是未处理
    @GetMapping("purchase/findAllPurchase")
    public ResponseResult<PageInfo<Purchase>> findAllDownDrug(Purchase purchase,
                                                          @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                          @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Purchase> list = purchaseService.findAllPur(purchase);
        PageInfo purPageInfo = new PageInfo(list);
        return new ResponseResult<PageInfo<Purchase>>(purPageInfo,"OK",200);
    }
}
