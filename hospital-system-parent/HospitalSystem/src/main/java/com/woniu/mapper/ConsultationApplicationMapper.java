package com.woniu.mapper;

import com.woniu.entity.ConsultationApplication;
import com.woniu.entity.ConsultationApplicationExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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

    //医生站编辑会诊信息
    @Update("update HOS_consultation_application set consultation_date=#{consultationDate}, consultation_category_id=#{consultationCategoryId},status=#{status}," +
            "consultation_emergency_id=#{consultationEmergencyId}, `desc`=#{desc} ,reason=#{reason} where id=#{id}")
    void updateConsultationApplication(ConsultationApplication consultationApplication);

    //医生站取消会诊
    @Update("update HOS_consultation_application set status = 3 where id=#{id}")
    void cancelConsultationApplication(Integer id);


    //突发情况,申请会诊
    @Insert("insert into HOS_consultation_application(doctor_id,patient_id,consultation_date,reason,`desc`,consultation_emergency_id,consultation_category_id,status) values(#{doctorId},#{id},#{consultationDate},#{reason},#{desc},#{consultationEmergencyId},#{consultationCategoryId},#{status})")
    void addConsultationApplication(ConsultationApplication consultationApplication);

}