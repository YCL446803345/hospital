package com.woniu.service.impl;

import com.woniu.entity.Purchase;
import com.woniu.entity.PurchaseExample;
import com.woniu.mapper.PurchaseMapper;
import com.woniu.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 药品采购业务类
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    //动态添加采购信息
    public void addPurchase(Purchase purchase) {
        Random random = new Random();
        if (StringUtils.isEmpty(purchase.getPurchaseName())){
            purchase.setStatus("0");
            purchase.setStartTime(new Date());
            purchase.setId(random.nextInt(10000));
        }
        purchaseMapper.insert(purchase);
    }

    // 动态查询采购清单
    public List<Purchase> findAllPur(Purchase purchase) {

        PurchaseExample purchaseExample = new PurchaseExample();
        PurchaseExample.Criteria criteria = purchaseExample.createCriteria();

        if (purchase.getSpare1().equals("0")){
            criteria.andStatusEqualTo("0");
        }
        if (purchase.getSpare1().equals("1")){
            criteria.andStatusEqualTo("1");
        }
        List<Purchase> list = purchaseMapper.selectByExample(purchaseExample);
        return list;
    }
}
