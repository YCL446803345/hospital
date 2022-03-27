package com.woniu.mapper;

import com.woniu.entity.Prescription;
import com.woniu.entity.PrescriptionDrug;
import com.woniu.entity.PrescriptionExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}