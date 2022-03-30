package com.woniu.mapper;

import com.woniu.entity.Drug;
import com.woniu.entity.Patient;
import com.woniu.entity.PrescriptionBill;
import com.woniu.entity.PrescriptionBillExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
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
            "        where pr.patient_id = p.id  and pr.id = pb.prescription_id and p.id=#{id} and pb.status=#{status}")
    List<Float> selectMoneyByPatientId (@Param("id") Integer id,@Param("status") String status);

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

    @Select("select  d.name name,d.drug_type drugType,d.make_price makePrice,d.sale_price salePrice,\n" +
            "d.specifications specifications,p.create_time createTime,pg.num num \n" +
            "from  HOS_prescription p\n" +
            "left join HOS_prescription_bill pb on pb.prescription_id=p.id\n" +
            "left join HOS_patient pa on p.patient_id=pa.id\n" +
            "left join HOS_prescription_drug pg on p.id=pg.prescription_id\n" +
            "left join HOS_drug d on d.id=pg.drug_id\n" +
            "where pb.id is not null and p.patient_id=#{id}")
    List<Drug> getPrescriptionBill(Patient patient);

    @Select("  select pb.*\n" +
            " from HOS_prescription_bill pb,HOS_patient p,HOS_prescription pr\n" +
            " where p.id = pr.patient_id and p.`status` in (1,2,4) and pr.id = pb.prescription_id")
    List<PrescriptionBill> selectInPatientAll ();

    @Select("  select pb.*\n" +
            " from HOS_prescription_bill pb,HOS_patient p,HOS_prescription pr\n" +
            " where p.id = pr.patient_id and p.`status` = 3 and pr.id = pb.prescription_id")
    List<PrescriptionBill> selectOutPatientAll ( );

    //下达处方新增处方账单
    @Insert("insert into HOS_prescription_bill(prescription_id,money,status) values(#{prescriptionId},#{money},#{status})")
    void addPrescriptionBill(PrescriptionBill prescriptionBill);
}