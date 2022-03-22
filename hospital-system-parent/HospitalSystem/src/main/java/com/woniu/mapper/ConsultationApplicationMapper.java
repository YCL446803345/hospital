package com.woniu.mapper;

import com.woniu.entity.ConsultationApplication;
import com.woniu.entity.ConsultationApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsultationApplicationMapper {
    long countByExample(ConsultationApplicationExample example);

    int deleteByExample(ConsultationApplicationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsultationApplication record);

    int insertSelective(ConsultationApplication record);

    List<ConsultationApplication> selectByExample(ConsultationApplicationExample example);

    ConsultationApplication selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsultationApplication record, @Param("example") ConsultationApplicationExample example);

    int updateByExample(@Param("record") ConsultationApplication record, @Param("example") ConsultationApplicationExample example);

    int updateByPrimaryKeySelective(ConsultationApplication record);

    int updateByPrimaryKey(ConsultationApplication record);
}