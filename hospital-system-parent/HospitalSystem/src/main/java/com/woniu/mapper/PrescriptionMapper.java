package com.woniu.mapper;

import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.entity.PrescriptionExample;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionMapper {

    long countByExample(PrescriptionExample example);

    int deleteByExample(PrescriptionExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(Prescription record);
    int insertSelective(Prescription record);

    List<Prescription> selectByExample(PrescriptionExample example);
    Prescription selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Prescription record, @Param("example") PrescriptionExample example);
    int updateByExample(@Param("record") Prescription record, @Param("example") PrescriptionExample example);
    int updateByPrimaryKeySelective(Prescription record);
    int updateByPrimaryKey(Prescription record);

    //药房通过条件查询处方
    @Select("<script>"+
            "select p.*,w1.name doctorName,w2.name nurseName,d.name drugs,pa.name paName,pd.num pdNum\n"+
            "from HOS_prescription p\n"+
            "left join HOS_prescription_drug pd on p.id=pd.prescription_id\n"+
            "left join HOS_drug d on d.id=pd.drug_id\n"+
            "left join HOS_worker w1 on w1.id=p.doctor_id\n"+
            "left join HOS_patient pa on pa.id=p.patient_id\n"+
            "left join HOS_worker w2 on w2.id=pa.nurse_id\n"+
            "<where>"+
            "<if test='doctorName != null and doctorName != \"\"'>and w1.name like '%${doctorName}'</if>"+
            "<if test='nurseName != null and nurseName != \"\"'>and w2.name like '%${nurseName}'</if>"+
            "<if test='preName != null and preName != \"\"'>and pa.name like '%${preName}'</if>"+
            "and p.prescription_status = 1"+
            "</where>"+
            "</script>")
    List<Prescription> getPreDrugByMany(@Param("doctorName")String doctorName, @Param("nurseName")String nurseName,@Param("preName") String preName);

    @Select("select p.*,w.name doctorName from HOS_prescription p left join HOS_worker w on w.id=p.doctor_id where p.id=#{pid}")
    Prescription getPreInfo(Integer pid);

    @Select("select d.name drugName,stock,num from HOS_prescription p\n" +
            "\t\t\t\t\t\t\tleft join HOS_prescription_drug pd on p.id=pd.prescription_id\n" +
            "\t\t\t\t\t\t\tleft join HOS_drug d on d.id = pd.drug_id\n" +
            "\t\t\t\t\t\t\twhere p.id=#{pid}")
    List<Prescription> byPidfindStockAndNum(Integer pid);


    @Select("<script>" +
            "select p.id id,p.doctor_id doctorId,w.name doctorName,p.create_time createTime,\n" +
            "p.prescription_status prescriptionStatus,\n" +
            "p.spare1 spare1,p.spare2 spare2 from HOS_prescription p \n" +
            "left join HOS_worker w on p.doctor_id=w.id\n" +
            "left join HOS_patient pa on p.patient_id=pa.id\n" +
            "where pa.id=#{patientId}" +
            "<if test='prescriptionStatus!=null and prescriptionStatus != \"\"'>and p.prescription_status =#{prescriptionStatus}</if>" +
            "</script>")
    List<Prescription> findPatients(Prescription prescription);

    @Update("update HOS_prescription set prescription_status=2 where id=#{id}")
    void checkPrescription(Integer id);

    @Update("update HOS_prescription set prescription_status=4 where id=#{id}")
    void doPrescription(Integer id);

    @Update("update HOS_prescription set prescription_status=5 where id=#{id}")
    void stopPrescription(Integer id);

    //分页模糊查询处方列表
    List<Prescription> queryPrescriptionList(Prescription prescription);


    //下达处方
    @Insert("insert into HOS_prescription(doctor_id,patient_id,create_time,prescription_status) values(#{doctorId},#{patientId},#{createTime},#{prescriptionStatus})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addPrescription(Prescription prescription);


}