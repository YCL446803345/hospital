package com.woniu.service.impl;

import com.woniu.entity.*;
import com.woniu.mapper.DrugMapper;
import com.woniu.mapper.DrugOutMapper;
import com.woniu.mapper.WithdrawalDrugMapper;
import com.woniu.mapper.WorkerMapper;
import com.woniu.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 药品信息业务类
 */
@Service("drugService")
@Transactional
public class DrugServiceImpl implements DrugService {


    @Autowired
    private DrugMapper drugMapper;
    @Autowired
    private DrugOutMapper drugOutMapper;
    @Autowired
    private WithdrawalDrugMapper withdrawalDrugMapper;
    @Autowired
    private WorkerMapper workerMapper;

    //查询所有药品信息,带分页带条件查询
    public List<Drug> findAllDrug(Drug drug) {
        DrugExample drugExample = new DrugExample();
        DrugExample.Criteria criteria = drugExample.createCriteria();

        if (!StringUtils.isEmpty(drug.getDrugType())) {
            criteria.andDrugTypeEqualTo(drug.getDrugType());
        }
        if (!StringUtils.isEmpty(drug.getName())) {
            criteria.andNameLike("%" + drug.getName() + "%");
        }
        //上下架处理
        if (!StringUtils.isEmpty(drug.getStatus())) {
            criteria.andStatusEqualTo(drug.getStatus());
        } else {
            criteria.andStatusEqualTo("1");
        }
        if (!StringUtils.isEmpty(drug.getSalePrice())) {
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
        if (!StringUtils.isEmpty(drug.getDrugType())) {
            criteria.andDrugTypeEqualTo(drug.getDrugType());
        }
        if (!StringUtils.isEmpty(drug.getName())) {
            criteria.andNameLike("%" + drug.getName() + "%");
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

    @Override
    public List<Drug> getDrugsByPrescriptionId(Integer id) {
        List<Drug> drugs = drugMapper.getDrugsByPrescriptionId(id);
        for (Drug drug : drugs) {
            drug.setMoney(drug.getSalePrice() * drug.getNum());
        }
        return drugs;
    }

    //获取所有药品列表
    public List<Drug> findDrugList() {
        return drugMapper.selectByExample(null);
    }

    //根据药品id加库存
    public void addDrugStockById(Drug drug) {
        Drug drug1 = drugMapper.selectByPrimaryKey(drug.getId());
        drugMapper.addDrugStockById(drug.getId(),drug.getStock()+drug1.getStock());
    }

    //根据姓名查药品
    public List<Drug> byNameGetDrug(String name) {
        DrugExample drugExample = new DrugExample();
        DrugExample.Criteria criteria = drugExample.createCriteria();
        criteria.andNameEqualTo(name);

        List<Drug> drugList = drugMapper.selectByExample(drugExample);
        return drugList;
    }

    //根据药品姓名加库存
    public void updateStock(String name, int i) {
        drugMapper.updateStock(i,name);
    }

    //单个退药申请,改状态,加库存,加退药记录
    public void batchUpdateStock(String[] split,String account,Integer did) {
        //查询当前操作人姓名
        Worker worker = workerMapper.selectWorkerByAccount(account);
        //改退药状态
        drugOutMapper.updateStatusById(did);
        //加库存
        for (String s : split) {
            String[] split1 = s.split("-");
            Integer id = Integer.parseInt(split1[0]);
            Drug drug = drugMapper.selectByPrimaryKey(id);
            Integer stock = Integer.parseInt(split1[1]);
            drugMapper.addDrugStockById(id,drug.getStock()+stock);
        }
        //加退药记录
        WithdrawalDrug wd = new WithdrawalDrug();
        wd.setDrugOutId(did);
        wd.setOutName(worker.getName());
        wd.setOutDrugTime(new Date());
        withdrawalDrugMapper.insert(wd);
    }

    //删除药品
    public void deleteDrugById(Integer id) {
        drugMapper.deleteByPrimaryKey(id);
    }

    //批量退药、改状态、加库存、批量添加退药记录
    public void batchUpdate(List<Integer> ids, String account) {
        //查询操作人
        Worker worker = workerMapper.selectWorkerByAccount(account);

        //迭代ids,查询所有退药申请对应的药品名称和数量
        for (Integer id : ids) {
            //修改退药申请状态
            drugOutMapper.updateStatusById(id);

            //加退药记录
            WithdrawalDrug wd = new WithdrawalDrug();
            wd.setDrugOutId(id);
            wd.setOutName(worker.getName());
            wd.setOutDrugTime(new Date());
            wd.setSpare1("已退药");
            withdrawalDrugMapper.insert(wd);

            //加库存
            List<DrugOut> drugOutList = drugOutMapper.getDidAndStockAndNum(id);
            for (DrugOut drugOut : drugOutList) {
                drugMapper.addDrugStockById(drugOut.getDid(),drugOut.getDstock()+drugOut.getDnum());
            }
        }

    }
}
