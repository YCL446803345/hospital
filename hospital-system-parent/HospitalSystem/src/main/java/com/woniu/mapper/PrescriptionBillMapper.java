package com.woniu.mapper;

import com.woniu.entity.PrescriptionBill;
import com.woniu.entity.PrescriptionBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionBillMapper {
    long countByExample(PrescriptionBillExample example);

    int deleteByExample(PrescriptionBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrescriptionBill record);

    int insertSelective(PrescriptionBill record);

    List<PrescriptionBill> selectByExample(PrescriptionBillExample example);

    PrescriptionBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrescriptionBill record, @Param("example") PrescriptionBillExample example);

    int updateByExample(@Param("record") PrescriptionBill record, @Param("example") PrescriptionBillExample example);

    int updateByPrimaryKeySelective(PrescriptionBill record);

    int updateByPrimaryKey(PrescriptionBill record);


    @Select("        select pb.money\n" +
            "        from HOS_prescription pr,HOS_prescription_bill pb,HOS_patient p\n" +
            "        where pr.patient_id = p.id  and pr.id = pb.prescription_id and p.id=#{id} and pb.status=1")
    List<Float> selectMoneyByPatientId (Integer id);

    @Update("update \n" +
            "HOS_prescription pr,HOS_prescription_bill pb,HOS_patient p\n" +
            "set pb.`status` = 2\n" +
            "where pr.patient_id = p.id  and pr.id = pb.prescription_id and p.id=#{id}")
    void updateStatus (Integer id);

    @Update("update \n" +
            "HOS_prescription pr,HOS_prescription_bill pb,HOS_patient p\n" +
            "set pb.spare1 = #{newTime}\n" +
            "where pr.patient_id = p.id  and pr.id = pb.prescription_id and p.id=#{id}")
    void updateDate (@Param("newTime") String nowTime,@Param("id")Integer id);
}