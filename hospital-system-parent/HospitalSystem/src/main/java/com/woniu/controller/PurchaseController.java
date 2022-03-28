package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Drug;
import com.woniu.entity.Purchase;
import com.woniu.service.DrugService;
import com.woniu.service.PurchaseService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private DrugService drugService;

    //添加采购清单
    @PostMapping("purchase/addPurchase")
    public ResponseResult addPurchase(@RequestBody Purchase purchase){
        purchaseService.addPurchase(purchase);
        return ResponseResult.ok();
    }

    //动态查询采购清单
    @GetMapping("purchase/findAllPurchase")
    public ResponseResult<PageInfo<Purchase>> findAllDownDrug(Purchase purchase,
                                                          @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                                          @RequestParam(name = "pageSize",defaultValue = "5")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Purchase> list = purchaseService.findAllPur(purchase);
        PageInfo purPageInfo = new PageInfo(list);
        return new ResponseResult<PageInfo<Purchase>>(purPageInfo,"OK",200);
    }

    //根据药品id直接加库存
    @PostMapping("purchase/addDrugStock")
    public ResponseResult addDrugStock(Drug drug){
        drugService.addDrugStockById(drug);
        return ResponseResult.ok();
    }


}
