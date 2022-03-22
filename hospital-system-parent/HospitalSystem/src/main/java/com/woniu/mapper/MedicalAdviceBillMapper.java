package com.woniu.mapper;

import com.woniu.entity.MedicalAdviceBill;
import com.woniu.entity.MedicalAdviceBillExample;
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
            "where p.id = ma.patient_id and ma.id = mab.medical_advice_id and p.id = #{id}")
    List<Float> selectMoneyByPatientId (Integer id);

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
}