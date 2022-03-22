package com.woniu.mapper;

import com.woniu.entity.MedicalAdvice;
import com.woniu.entity.MedicalAdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MedicalAdviceMapper {
    long countByExample(MedicalAdviceExample example);

    int deleteByExample(MedicalAdviceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MedicalAdvice record);

    int insertSelective(MedicalAdvice record);

    List<MedicalAdvice> selectByExample(MedicalAdviceExample example);

    MedicalAdvice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MedicalAdvice record, @Param("example") MedicalAdviceExample example);

    int updateByExample(@Param("record") MedicalAdvice record, @Param("example") MedicalAdviceExample example);

    int updateByPrimaryKeySelective(MedicalAdvice record);

    int updateByPrimaryKey(MedicalAdvice record);
}