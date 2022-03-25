package com.woniu.mapper;

import com.woniu.entity.HospitalizationBill;
import com.woniu.entity.HospitalizationBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Update;

import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationBillMapper {
    long countByExample(HospitalizationBillExample example);

    int deleteByExample(HospitalizationBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HospitalizationBill record);

    int insertSelective(HospitalizationBill record);

    List<HospitalizationBill> selectByExample(HospitalizationBillExample example);

    HospitalizationBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HospitalizationBill record, @Param("example") HospitalizationBillExample example);

    int updateByExample(@Param("record") HospitalizationBill record, @Param("example") HospitalizationBillExample example);

    int updateByPrimaryKeySelective(HospitalizationBill record);

    int updateByPrimaryKey(HospitalizationBill record);

    @Update(" update\n" +
            " HOS_hospitalization_bill hb,HOS_patient p\n" +
            " set hb.pay_days = #{days}\n" +
            " where hb.patient_id = #{id}")
    void updateDays (@Param("days") int days,@Param("id") int id);

    @Update(" update \n" +
            " HOS_patient p , HOS_hospitalization_bill hb\n" +
            " set hb.spare1 = #{newTime}\n" +
            " where hb.patient_id = p.id and p.id = #{id}")
    void updateDate (@Param("newTime")String newTime, @Param("id")Integer id);
}