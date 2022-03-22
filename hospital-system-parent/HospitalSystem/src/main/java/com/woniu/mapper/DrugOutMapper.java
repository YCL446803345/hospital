package com.woniu.mapper;

import com.woniu.entity.DrugOut;
import com.woniu.entity.DrugOutExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DrugOutMapper {
    long countByExample(DrugOutExample example);

    int deleteByExample(DrugOutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrugOut record);

    int insertSelective(DrugOut record);

    List<DrugOut> selectByExample(DrugOutExample example);

    DrugOut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrugOut record, @Param("example") DrugOutExample example);

    int updateByExample(@Param("record") DrugOut record, @Param("example") DrugOutExample example);

    int updateByPrimaryKeySelective(DrugOut record);

    int updateByPrimaryKey(DrugOut record);
}