package com.woniu.service.impl;

import com.woniu.entity.Drug;
import com.woniu.entity.DrugExample;
import com.woniu.mapper.DrugMapper;
import com.woniu.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 药品信息业务类
 */
@Service("drugService")
public class DrugServiceImpl implements DrugService {


    @Autowired
    private DrugMapper drugMapper;

    //查询所有药品信息,带分页带条件查询
    public List<Drug> findAllDrug(Drug drug) {
        DrugExample drugExample = new DrugExample();
        DrugExample.Criteria criteria = drugExample.createCriteria();

        if (!StringUtils.isEmpty(drug.getDrugType())){
            criteria.andDrugTypeEqualTo(drug.getDrugType());
        }
        if (!StringUtils.isEmpty(drug.getName())){
            criteria.andNameLike("%"+drug.getName()+"%");
        }
        //上下架处理
        if (!StringUtils.isEmpty(drug.getStatus())){
            criteria.andStatusEqualTo(drug.getStatus());
        }else {
            criteria.andStatusEqualTo("0");
        }
        if (!StringUtils.isEmpty(drug.getSalePrice())){
            criteria.andSalePriceGreaterThan(drug.getSalePrice());
        }
        List<Drug> drugList = drugMapper.selectByExample(drugExample);
        return drugList;
    }

    //修改药品信息
    public void updateDrug(Drug drug) {
        drugMapper.updateByPrimaryKeySelective(drug);
    }

    //添加药品
    public void addDrug(Drug drug) {
        drugMapper.insert(drug);
    }

    @Override
    public List<Drug> getDrugsByPrescriptionId(Integer id) {
        List<Drug> drugs=drugMapper.getDrugsByPrescriptionId(id);
        for (Drug drug : drugs) {
            drug.setMoney(drug.getSalePrice()*drug.getNum());
        }
        return drugs;
    }
}
