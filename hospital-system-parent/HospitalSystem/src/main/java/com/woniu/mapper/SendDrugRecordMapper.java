package com.woniu.mapper;

import com.woniu.entity.SendDrugRecord;
import com.woniu.entity.SendDrugRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SendDrugRecordMapper {

    long countByExample(SendDrugRecordExample example);

    int deleteByExample(SendDrugRecordExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(SendDrugRecord record);
    int insertSelective(SendDrugRecord record);

    List<SendDrugRecord> selectByExample(SendDrugRecordExample example);
    SendDrugRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SendDrugRecord record, @Param("example") SendDrugRecordExample example);
    int updateByExample(@Param("record") SendDrugRecord record, @Param("example") SendDrugRecordExample example);
    int updateByPrimaryKeySelective(SendDrugRecord record);
    int updateByPrimaryKey(SendDrugRecord record);

    void addSenDrugBatch(List<SendDrugRecord> list);

    @Select("<script>" +
            "select p.id pid, w.name doctorName,pa.name preName,sdr.send_drug_time sdTime,sdr.spare1 spare1,sdr.spare2 spare2\n" +
            "from HOS_send_drug_record sdr\n" +
            "left join HOS_prescription p on p.id=sdr.prescription_id\n" +
            "left join HOS_worker w on w.id=p.doctor_id\n" +
            "left join HOS_patient pa on pa.id=p.patient_id\n" +
            "where 1 = 1"+
            "<if test='name!=null and name != \"\"'>and spare1 like '%${name}%'</if>" +
            "</script>")
    List<SendDrugRecord> findAllSendRecord(String name);
}