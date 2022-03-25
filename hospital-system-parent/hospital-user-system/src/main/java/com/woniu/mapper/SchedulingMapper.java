package com.woniu.mapper;

import com.woniu.entity.Scheduling;
import com.woniu.entity.SchedulingExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SchedulingMapper {
    long countByExample(SchedulingExample example);

    int deleteByExample(SchedulingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Scheduling record);

    int insertSelective(Scheduling record);

    List<Scheduling> selectByExample(SchedulingExample example);

    Scheduling selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByExample(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByPrimaryKeySelective(Scheduling record);

    int updateByPrimaryKey(Scheduling record);
    @Select("select w.name remarks,s.worktime from HOS_scheduling s\n" +
            "inner join HOS_worker w on w.spare1=s.id\n")
    List<Scheduling> select(SchedulingExample schedulingExample);
    @Update("update HOS_worker SET spare1 = #{spare1} WHERE id = #{id} ")
    void update(Scheduling scheduling);
}