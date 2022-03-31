package com.woniu.mapper;

import com.woniu.entity.DrugType;
import com.woniu.entity.DrugTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DrugTypeMapper {
    long countByExample(DrugTypeExample example);

    int deleteByExample(DrugTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrugType record);

    int insertSelective(DrugType record);

    List<DrugType> selectByExample(DrugTypeExample example);

    DrugType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrugType record, @Param("example") DrugTypeExample example);

    int updateByExample(@Param("record") DrugType record, @Param("example") DrugTypeExample example);

    int updateByPrimaryKeySelective(DrugType record);

    int updateByPrimaryKey(DrugType record);
}