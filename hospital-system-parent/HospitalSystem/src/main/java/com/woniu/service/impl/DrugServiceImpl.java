package com.woniu.service.impl;

import com.woniu.entity.Drug;
import com.woniu.entity.DrugExample;
import com.woniu.mapper.DrugMapper;
import com.woniu.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
            criteria.andStatusEqualTo("1");
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

    //by药品id查询药品信息
    public Drug getDrugById(Integer id) {
        Drug drug = drugMapper.selectByPrimaryKey(id);
        return drug;
    }

    @Override
    public List<Drug> findAllDownDrug(Drug drug) {

        DrugExample drugExample = new DrugExample();
        DrugExample.Criteria criteria = drugExample.createCriteria();
        criteria.andStatusEqualTo("0");
        if (!StringUtils.isEmpty(drug.getDrugType())){
            criteria.andDrugTypeEqualTo(drug.getDrugType());
        }
        if (!StringUtils.isEmpty(drug.getName())){
            criteria.andNameLike("%"+drug.getName()+"%");
        }
        List<Drug> drugList = drugMapper.selectByExample(drugExample);
        return drugList;
    }

    //查询药品所有类型
    public List<String> finAllType() {
        List<String> strings = drugMapper.findAllDrugType();
        return strings;
    }

    //修改药品整体信息
    public void update(Drug updateDrug) {
        drugMapper.updateByPrimaryKeySelective(updateDrug);
    }

    //批量上架
    public void updateByIdBatchStatus(List<String> strings) {
        List<Integer> ids = new ArrayList<>();
        for (String string : strings) {
            int i = Integer.parseInt(string);
            ids.add(i);
        }
        drugMapper.updateByIdBatchStatus(ids);
    }
}
