package com.woniu.mapper;

import com.woniu.entity.Patient;
import com.woniu.entity.PatientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PatientMapper {
    long countByExample(PatientExample example);

    int deleteByExample(PatientExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Patient record);

    int insertSelective(Patient record);

    List<Patient> selectByExample(PatientExample example);

    Patient selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Patient record, @Param("example") PatientExample example);

    int updateByExample(@Param("record") Patient record, @Param("example") PatientExample example);

    int updateByPrimaryKeySelective(Patient record);

    int updateByPrimaryKey(Patient record);

    @Select("<script>" +
            "select p.id id,p.name name,no,age,card_id cardId,appointmentt_time appointmenttTime,base_desc baseDesc,phone,p.gender gender,\n" +
            "code bedCode,d.name deptName,p.nurse_id nurseId,p.bed_id bedId,p.doctor_id doctorId,p.dept_id deptId " +
            " ,w1.name nurseName,w2.name doctorName \n" +
            "from HOS_patient p,HOS_worker w1,HOS_worker w2,HOS_dept d,HOS_bed b \n" +
            "where p.nurse_id=w1.id and p.doctor_id=w2.id and p.bed_id=b.id and b.dept_id=d.id" +
            "<if test='name!=null and name != \"\"'>and p.name like '%${name}%'</if>" +
            "<if test='no!=null and no != \"\"'>and p.no like '%${no}%'</if>" +
            "<if test='cardId!=null and cardId != \"\"'>and p.card_id like '%${cardId}%'</if>" +
            "<if test='gender!=null and gender != \"\"'>and p.gender = #{gender}</if>" +
            "<if test='status!=null and status != \"\"'>and p.status = #{status}</if>" +
            "</script>")
    List<Patient> findPatients(Patient patient);

    @Update("update HOS_patient set dept_id=#{deptId},bed_id=#{bedId} where id=#{id}")
    void updateBed(Patient patient);

    @Update("update HOS_patient set doctor_id=#{doctorId},nurse_id=#{nurseId} where id=#{id}")
    void updateDoctorOrNurse(Patient patient);
}