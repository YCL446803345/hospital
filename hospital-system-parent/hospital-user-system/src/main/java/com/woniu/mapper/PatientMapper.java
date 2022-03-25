package com.woniu.mapper;

import com.woniu.entity.Patient;
import com.woniu.entity.PatientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
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


    @Select("<script>" +
            "select p.id id,p.name name,no,age,card_id cardId,appointmentt_time appointtTime,base_desc baseDesc,phone,p.gender gender,p.dept_id deptId,\n" +
            "d.name deptName from  HOS_patient p ,HOS_dept d where p.dept_id=d.id and bed_id is null " +
            "<if test='name!=null and name != \"\"'>and p.name like '%${name}%'</if>" +
            "<if test='no!=null and no != \"\"'>and p.no like '%${no}%'</if>" +
            "<if test='cardId!=null and cardId != \"\"'>and p.card_id like '%${cardId}%'</if>" +
            "<if test='gender!=null and gender != \"\"'>and p.gender = #{gender}</if>" +
            "<if test='status!=null and status != \"\"'>and p.status = #{status}</if>" +
            "</script>")
    List<Patient> findPatientsWithNotBed(Patient patient);

    @Update("update HOS_patient set dept_id=#{deptId},bed_id=#{bedId},doctor_id=9,nurse_id=9 where id=#{id}")
    void updateBedByPatientWithOutBed(Patient patient);

    @Update("update HOS_patient set dept_id=#{deptId},bed_id=null,doctor_id=9,nurse_id=9 where id=#{id}")
    void stopUseBed(Patient patient);

    //医生站分页模糊查询患者列表
    List<Patient> queryPatientList(Patient patient);


    /**
     * 护士站模块
     *查询所有病人信息，方便护士长跟换病人科室
     * @param patient
     */
    @Select("<script>" +
            "select p.status status,p.id id,p.name name,no,p.bed_id bedId,p.dept_id deptId,age,card_id cardId," +
            "appointmentt_time appointmenttTime,base_desc baseDesc,phone,p.gender gender,\n" +
            "code bedCode,d.name deptName ,w1.name nurseName,w2.name doctorName from HOS_patient p \n" +
            "left join HOS_worker w1 on p.nurse_id=w1.id\n" +
            "left join HOS_worker w2 on p.doctor_id=w2.id\n" +
            "left join HOS_dept d on p.dept_id=d.id\n" +
            "left join HOS_bed b on p.bed_id=b.id where 1=1 " +
            "<if test='name!=null and name != \"\"'>and p.name like '%${name}%'</if>" +
            "<if test='no!=null and no != \"\"'>and p.no like '%${no}%'</if>" +
            "<if test='cardId!=null and cardId != \"\"'>and p.card_id like '%${cardId}%'</if>" +
            "<if test='gender!=null and gender != \"\"'>and p.gender = #{gender}</if>" +
            "<if test='status==null and status == \"\"'>and p.status  = #{1,2,3}</if>" +
            "<if test='status==1 '>and p.status in (1,2)</if>" +
            "<if test='status==2 '>and p.status in (3)</if>" +
            "<if test='deptId!=null and deptId != \"\"'>and p.dept_id = #{deptId}</if>" +
            "</script>")
    List<Patient> findPatientsByChangeDept(Patient patient);

    @Update("update HOS_patient set name=#{name},gender=#{gender},phone=#{phone},age=#{age}," +
            "base_desc=#{baseDesc},card_id=#{cardId} where id=#{id}")
    void updatePatient(Patient patient);

    @Select("<script>" +
            "select count(0) FROM HOS_patient WHERE status NOT LIKE 9 " +
            "<if test='name!=null and name != \"\"'>and name like '%${name}%'</if>" +
            "<if test='no!=null and no != \"\"'>and no like '%${no}%'</if>" +
            "</script>")
    int selectTotal (@Param("name") String name,@Param("no") String no);

}