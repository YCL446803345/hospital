package com.woniu.mapper;

import com.woniu.entity.Bed;
import com.woniu.entity.BedExample;
import java.util.List;

import com.woniu.entity.Patient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BedMapper {
    long countByExample(BedExample example);

    int deleteByExample(BedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bed record);

    int insertSelective(Bed record);

    List<Bed> selectByExample(BedExample example);

    Bed selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bed record, @Param("example") BedExample example);

    int updateByExample(@Param("record") Bed record, @Param("example") BedExample example);

    int updateByPrimaryKeySelective(Bed record);

    int updateByPrimaryKey(Bed record);

    @Select("select id,code from HOS_bed where dept_id=#{deptId} and status='1'")
    List<Bed> findBedsByDeptId(Integer deptId);

    @Update("update HOS_bed set status='2' where id=#{bedId}")
    void changeBedStatusOn(Patient patient);

    @Update("update HOS_bed set status='1' where id=#{bedId}")
    void changeBedStatusOff(Patient patient);
}