package com.woniu.service.impl;

import com.woniu.entity.WithdrawalDrug;
import com.woniu.mapper.WithdrawalDrugMapper;
import com.woniu.service.WithdrawalDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退药申请业务类
 */
@Service("withdrawalDrugService")
public class WithdrawalDrugServiceImpl implements WithdrawalDrugService {


    @Autowired
    private WithdrawalDrugMapper withdrawalDrugMapper;

    //查询所有退药记录
    public List<WithdrawalDrug> findAllRecord(String name) {
        List<WithdrawalDrug> withdrawalDrugs = withdrawalDrugMapper.findAllOutRecord(name);
        return withdrawalDrugs;
    }

}