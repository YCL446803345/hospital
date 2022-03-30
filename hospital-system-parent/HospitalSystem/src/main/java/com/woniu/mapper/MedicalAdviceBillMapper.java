package com.woniu.mapper;

import com.woniu.entity.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalAdviceBillMapper {
    long countByExample(MedicalAdviceBillExample example);

    int deleteByExample(MedicalAdviceBillExample example);

    int insert(MedicalAdviceBill record);

    int insertSelective(MedicalAdviceBill record);

    List<MedicalAdviceBill> selectByExample(MedicalAdviceBillExample example);

    int updateByExampleSelective(@Param("record") MedicalAdviceBill record, @Param("example") MedicalAdviceBillExample example);

    int updateByExample(@Param("record") MedicalAdviceBill record, @Param("example") MedicalAdviceBillExample example);

    @Select("select mab.money\n" +
            "from HOS_patient p,HOS_medical_advice ma,HOS_medical_advice_bill mab\n" +
            "where p.id = ma.patient_id and ma.id = mab.medical_advice_id and p.id = #{id} and mab.status = #{status}")
    List<Float> selectMoneyByPatientId (@Param("id")Integer id,@Param("status")String status);

    @Update("update \n" +
            "HOS_patient p,HOS_medical_advice ma,HOS_medical_advice_bill mab\n" +
            "set mab.`status` = 2\n" +
            "where p.id = ma.patient_id and ma.id = mab.medical_advice_id and p.id = #{id} ")
    void updateStatus (Integer id);

    @Update("update \n" +
            "HOS_patient p,HOS_medical_advice ma,HOS_medical_advice_bill mab\n" +
            "set mab.spare1 = #{newTime}\n" +
            "where p.id = ma.patient_id and ma.id = mab.medical_advice_id and p.id = #{id} ")
    void updateDate (@Param("newTime") String nowTime,@Param("id")Integer id);

    @Select(" select mab.*\n" +
            " from HOS_patient p,HOS_medical_advice ma,HOS_medical_advice_bill mab\n" +
            " where p.id = ma.patient_id and ma.id = mab.medical_advice_id and  p.`status` in (1,2,4)")
    List<MedicalAdviceBill> selectInPatientAll ();
    @Select(" select mab.*\n" +
            " from HOS_patient p,HOS_medical_advice ma,HOS_medical_advice_bill mab\n" +
            " where p.id = ma.patient_id and ma.id = mab.medical_advice_id and  p.`status` = 3")
    List<MedicalAdviceBill> selectOutPatientAll ( );

    @Select("select a.create_time createTime,p.name name,p.price price from HOS_medical_advice a\n" +
            "left join HOS_medical_advice_bill b on b.medical_advice_id=a.id\n" +
            "left join HOS_project p on a.project_id=p.id\n" +
            "where a.patient_id=#{id} and a.project_id is not null")
    List<Project> getMedicalAdviceBill(Patient patient);

    @Select(" select sum(p.price)\n" +
            " from HOS_medical_advice a\n" +
            "left join HOS_medical_advice_bill b on b.medical_advice_id=a.id\n" +
            "left join HOS_project p on a.project_id=p.id\n" +
            "where a.patient_id=#{id} and b.status=2 and a.project_id is not null")
    Double getMedicalAdviceBillHavePay(Patient patient);
}