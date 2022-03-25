package com.woniu.mapper;

import com.woniu.entity.MedicalAdviceCategory;
import com.woniu.entity.MedicalAdviceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MedicalAdviceCategoryMapper {
    long countByExample(MedicalAdviceCategoryExample example);

    int deleteByExample(MedicalAdviceCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MedicalAdviceCategory record);

    int insertSelective(MedicalAdviceCategory record);

    List<MedicalAdviceCategory> selectByExample(MedicalAdviceCategoryExample example);

    MedicalAdviceCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MedicalAdviceCategory record, @Param("example") MedicalAdviceCategoryExample example);

    int updateByExample(@Param("record") MedicalAdviceCategory record, @Param("example") MedicalAdviceCategoryExample example);

    int updateByPrimaryKeySelective(MedicalAdviceCategory record);

    int updateByPrimaryKey(MedicalAdviceCategory record);
}