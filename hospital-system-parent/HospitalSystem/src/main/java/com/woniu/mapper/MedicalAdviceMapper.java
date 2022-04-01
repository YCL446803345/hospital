package com.woniu.mapper;

import com.woniu.entity.MedicalAdvice;
import com.woniu.entity.MedicalAdviceExample;
import java.util.List;

import com.woniu.entity.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalAdviceMapper {
    long countByExample(MedicalAdviceExample example);

    int deleteByExample(MedicalAdviceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MedicalAdvice record);

    int insertSelective(MedicalAdvice record);

    List<MedicalAdvice> selectByExample(MedicalAdviceExample example);

    MedicalAdvice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MedicalAdvice record, @Param("example") MedicalAdviceExample example);

    int updateByExample(@Param("record") MedicalAdvice record, @Param("example") MedicalAdviceExample example);

    int updateByPrimaryKeySelective(MedicalAdvice record);

    int updateByPrimaryKey(MedicalAdvice record);


    @Select("  \n" +
            " select pr.name,pr.price\n" +
            " from HOS_medical_advice ma,HOS_project pr,HOS_patient p,HOS_medical_advice_bill mab\n" +
            " where p.id = ma.patient_id and ma.project_id = pr.id and p.id = #{id} and mab.medical_advice_id = ma.id and mab.status = 1 and advice_status = 4")
    List<Project> selectProjectByPatientId (int id);

    //医生站分页模糊查询医嘱列表
    List<MedicalAdvice> queryMedicalAdviceList(MedicalAdvice medicalAdvice);
    //医生站根据Id查询医嘱(关联查询详细)
    MedicalAdvice findMedicalAdviceById(Integer id);
    //编辑医嘱修改项目
    @Update("update HOS_medical_advice set project_id=#{projectId} where id=#{id}")
    void updateMedicalAdviceProject(MedicalAdvice medicalAdvice);
    //停止医嘱
    @Update("update HOS_medical_advice set advice_status = 4 where id=#{id}")
    void stopMedicalAdviceById(Integer id);
    //下达医嘱
    @Insert("insert into HOS_medical_advice(patient_id,doctor_id,advice_category,advice_description,create_time,advice_status) values(#{patientId},#{doctorId},#{adviceCategory},#{adviceDescription},#{createTime},#{adviceStatus})")
    void addMedicalAdvice(MedicalAdvice medicalAdvice);

    @Update("update HOS_medical_advice set advice_status=2 where id=#{id}")
    void checkMedicalAdvice(Integer id);

    @Update("update HOS_medical_advice set advice_status=4 where id=#{id}")
    void stopMedicalAdvice(Integer id);

    @Update("update HOS_medical_advice set advice_status=3 where id=#{id}")
    void doMedicalAdvice(Integer id);

    @Select("<script>" +
            "select m.id id,w.name doctorName,m.advice_category adviceCategory,p.name projectName,p.project_type projectType,\n" +
            "m.create_time createTime ,m.advice_description adviceDescription,m.advice_status adviceStatus\n" +
            "from HOS_medical_advice m\n" +
            "left join HOS_worker w on m.doctor_id=w.id\n" +
            "left join HOS_project p on m.project_id=p.id\n" +
            "where m.patient_id=#{patientId}\n" +
            "<if test='projectType!=null and projectType != \"\"'>and p.project_type = #{projectType}</if>" +
            "<if test='doctorName!=null and doctorName != \"\"'>and w.name like '%${doctorName}%'</if>" +
            "<if test='adviceStatus!=null and adviceStatus != \"\"'>and m.advice_status =#{adviceStatus}</if>" +
            "<if test='adviceCategory!=null and adviceCategory != \"\"'>and m.advice_category = #{adviceCategory}</if>" +
            "</script>")
    List<MedicalAdvice> getMedicalAdvices(MedicalAdvice medicalAdvice);

    @Select("select count(*) from HOS_medical_advice where advice_status in (1,2) and patient_id = #{id}")
    Integer getMedicalAdvicesNeedForCheckNum(Integer id);
}