package com.woniu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Override
    public List<Bed> findBeds(Bed bed) {
        List<Bed> beds = bedMapper.findBeds(bed);
        return beds;
    }

    @Override
    public void addBed(Integer deptId) {
        List<Bed> beds = bedMapper.findBeds(null);
        Bed bed = beds.get(beds.size() - 1);
        String code = bed.getCode();
        String str = code.substring(1);

        int i = Integer.parseInt(str);

        int numberOfDigits = String.valueOf(i).length();
        String c="";

        int b=i+1;

        for(i=0;i<4-numberOfDigits;i++){
            c=c+"0";
        }

        c="A"+c+b;
        bed.setCode(c);
        bed.setDeptId(deptId);
        bedMapper.addBed(bed);
    }
}
