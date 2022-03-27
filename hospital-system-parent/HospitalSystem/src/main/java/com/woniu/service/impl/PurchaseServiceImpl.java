package com.woniu.service.impl;

import com.woniu.entity.Purchase;
import com.woniu.entity.PurchaseExample;
import com.woniu.mapper.PurchaseMapper;
import com.woniu.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 药品采购业务类
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    //添加采购信息
    public void addPurchase(Purchase purchase) {
        if (StringUtils.isEmpty(purchase.getPurchaseName())){
            purchase.setStatus("0");
        }else {
            purchase.setStatus("1");
        }
        purchaseMapper.insertSelective(purchase);
    }

    //查询采购清单,默认未处理
    public List<Purchase> findAllPur(Purchase purchase) {


        PurchaseExample purchaseExample = new PurchaseExample();
        PurchaseExample.Criteria criteria = purchaseExample.createCriteria();

        if (StringUtils.isEmpty(purchase)){
            criteria.andStatusEqualTo("0");
        }else {
            criteria.andStatusEqualTo("1");
        }

        return null;
    }
}
