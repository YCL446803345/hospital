package com.woniu.service;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
import com.woniu.entity.Patient;

import java.util.List;

public interface BedService {
    List<Bed> findBedsByDeptId(Integer deptId);

    void changeBedStatusOff(Patient patient);

    List<Bed> findBeds(Bed bed);

    void addBed(Integer deptId);
}
