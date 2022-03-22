package com.woniu.mapper;

import com.woniu.entity.MedicalAdvice;
import com.woniu.entity.MedicalAdviceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
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

    //医生站分页模糊查询医嘱列表
    List<MedicalAdvice> queryMedicalAdviceList(MedicalAdvice medicalAdvice);
    //医生站根据Id查询医嘱(关联查询详细)
    MedicalAdvice findMedicalAdviceById(Integer id);
}