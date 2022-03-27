package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
import com.woniu.entity.Patient;
import com.woniu.mapper.BedMapper;
import com.woniu.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bedService")
public class BedServiceImpl implements BedService {
    @Autowired
    private BedMapper bedMapper;
    /**
     * 根据指定科室搜索床位
     * @return
     */
    @Override
    public List<Bed> findBedsByDeptId(Integer deptId) {
        return bedMapper.findBedsByDeptId(deptId);
    }

    @Override
    public void changeBedStatusOff(Patient patient) {
        bedMapper.changeBedStatusOff(patient);
    }

    @Override
    public PageInfo<Bed> findBeds(Bed bed, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Bed> beds = bedMapper.findBeds(bed);
        PageInfo<Bed> PageInfo = new PageInfo<>(beds);
        return PageInfo;
    }


    @Override
    public List<Bed> findBedList() {
        List<Bed> bedList = bedMapper.findBedList();
        System.err.println(bedList);
        return bedList;
    }
}
