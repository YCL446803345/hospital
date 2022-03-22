package com.woniu.mapper;

import com.woniu.entity.MedicalAdvice;
import com.woniu.entity.MedicalAdviceExample;
import java.util.List;

import com.woniu.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
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

    @Select("  \n" +
            " select pr.name,pr.price\n" +
            " from HOS_medical_advice ma,HOS_project pr,HOS_patient p,HOS_medical_advice_bill mab\n" +
            " where p.id = ma.patient_id and ma.project_id = pr.id and p.id = #{id} and mab.medical_advice_id = ma.id and mab.status = 1")
    List<Project> selectProjectByPatientId (int id);
}