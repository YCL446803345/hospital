package com.woniu.service.impl;

import com.woniu.entity.Bed;
import com.woniu.entity.BedExample;
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


}
