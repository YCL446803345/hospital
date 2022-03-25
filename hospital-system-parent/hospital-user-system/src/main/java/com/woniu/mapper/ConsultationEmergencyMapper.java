package com.woniu.mapper;

import com.woniu.entity.ConsultationEmergency;
import com.woniu.entity.ConsultationEmergencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsultationEmergencyMapper {
    long countByExample(ConsultationEmergencyExample example);

    int deleteByExample(ConsultationEmergencyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsultationEmergency record);

    int insertSelective(ConsultationEmergency record);

    List<ConsultationEmergency> selectByExample(ConsultationEmergencyExample example);

    ConsultationEmergency selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsultationEmergency record, @Param("example") ConsultationEmergencyExample example);

    int updateByExample(@Param("record") ConsultationEmergency record, @Param("example") ConsultationEmergencyExample example);

    int updateByPrimaryKeySelective(ConsultationEmergency record);

    int updateByPrimaryKey(ConsultationEmergency record);
}