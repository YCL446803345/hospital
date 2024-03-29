package com.woniu.mapper;

import com.woniu.entity.NursingRecord;
import com.woniu.entity.NursingRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface NursingRecordMapper {
    long countByExample(NursingRecordExample example);

    int deleteByExample(NursingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NursingRecord record);

    int insertSelective(NursingRecord record);

    List<NursingRecord> selectByExample(NursingRecordExample example);

    NursingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NursingRecord record, @Param("example") NursingRecordExample example);

    int updateByExample(@Param("record") NursingRecord record, @Param("example") NursingRecordExample example);

    int updateByPrimaryKeySelective(NursingRecord record);

    int updateByPrimaryKey(NursingRecord record);


    @Insert("insert into HOS_nursing_record" +
            "(patient_id,nurse_id,nursing_level,create_time,decs,temperature,weight,breathe,pulse,take_medicine)" +
            "values" +
            "(#{patientId},#{nurseId},#{nursingLevel},#{createTime},#{decs},#{temperature},#{weight},#{breathe}," +
            "#{pulse},#{takeMedicine})")
    void addNursingRecord(NursingRecord nursingRecord);


    @Select("<script>" +
            "select p.id patientId,n.modify_time modifyTime,w1.name modifyName,w.name nurseName,p.name patientName,n.id id,n.nursing_level,n.create_time createTime\n" +
            ",n.decs decs,n.temperature temperature,n.weight weight,\n" +
            "n.breathe breathe,n.pulse pulse,n.take_medicine takeMedicine\n" +
            "from HOS_nursing_record n\n" +
            "left join HOS_patient p on n.patient_id=p.id\n" +
            "left join HOS_worker w on n.nurse_id=w.id\n" +
            "left join HOS_worker w1 on n.modify_nurse_id=w1.id\n" +
            "where p.id=#{patientId}" +
            "<if test='nursingLevel!=null and nursingLevel != \"\"'>and n.nursing_level = #{nursingLevel}</if>" +
            "</script>")
    List<NursingRecord> findNursingRecordsByPatientId(NursingRecord nursingRecord);

    @Update("update HOS_nursing_record set decs=#{decs},nursing_level=#{nursingLevel}," +
            "temperature=#{temperature},weight=#{weight},breathe=#{breathe},pulse=#{pulse}," +
            "take_medicine=#{takeMedicine},modify_nurse_id=#{nurseId},modify_time=#{modifyTime} where " +
            "id=#{id}")
    void updateNursingRecord(NursingRecord nursingRecord);
}