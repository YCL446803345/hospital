package com.woniu.mapper;

import com.woniu.entity.ConsultationApplication;
import com.woniu.entity.ConsultationApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
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

    //医生站分页模糊查询会诊列表
    List<ConsultationApplication> queryConsultationApplicationList(ConsultationApplication consultationApplication);
}