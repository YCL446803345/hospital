package com.woniu.mapper;

import com.woniu.entity.ConsultationCategor;
import com.woniu.entity.ConsultationCategorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsultationCategorMapper {
    long countByExample(ConsultationCategorExample example);

    int deleteByExample(ConsultationCategorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsultationCategor record);

    int insertSelective(ConsultationCategor record);

    List<ConsultationCategor> selectByExample(ConsultationCategorExample example);

    ConsultationCategor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsultationCategor record, @Param("example") ConsultationCategorExample example);

    int updateByExample(@Param("record") ConsultationCategor record, @Param("example") ConsultationCategorExample example);

    int updateByPrimaryKeySelective(ConsultationCategor record);

    int updateByPrimaryKey(ConsultationCategor record);
}