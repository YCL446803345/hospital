package com.woniu.mapper;

import com.woniu.entity.DrugOut;
import com.woniu.entity.DrugOutExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;

import org.springframework.stereotype.Repository;

@Repository
public interface DrugOutMapper {

    long countByExample(DrugOutExample example);

    int deleteByExample(DrugOutExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(DrugOut record);
    int insertSelective(DrugOut record);

    List<DrugOut> selectByExample(DrugOutExample example);
    DrugOut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrugOut record, @Param("example") DrugOutExample example);
    int updateByExample(@Param("record") DrugOut record, @Param("example") DrugOutExample example);
    int updateByPrimaryKeySelective(DrugOut record);
    int updateByPrimaryKey(DrugOut record);


    @Select("<script>" +
            "select do.id did,w.name doctorName,p.name preName,do.create_time cTime,do.out_status oStatus,do.out_cause oCause\n" +
            "from HOS_drug_out do\n" +
            "left join HOS_worker w on w.id=do.doctor_id\n" +
            "left join HOS_patient p on p.id=do.patient_id\n" +
            "left join HOS_drug_out_drug dod on dod.drug_out_id=do.id\n" +
            "left join HOS_drug d on d.id=dod.drug_id\n" +
            "where 1 = 1 and do.out_status = 2"+
            "<if test='doctorName!=null and doctorName != \"\"'>and w.name like '%${doctorName}%'</if>" +
            "<if test='preName!=null and preName != \"\"'>and p.name like '%${preName}%'</if>" +
            "group by did"+
            "</script>")
    List<DrugOut> findAllDrugList(@Param("doctorName") String doctorName, @Param("preName") String preName);

    //分页模糊查询退药列表
    List<DrugOut> queryDrugOutList(DrugOut drugOut);

    //申请退药,退药记录表新增数据
    @Insert("insert into HOS_drug_out(doctor_id,patient_id,out_status,create_time,out_cause) values(#{doctorId},#{patientId},#{outStatus},#{createTime},#{outCause})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addDrugOut(DrugOut drugOut);
    //撤销退药
    @Update("update HOS_drug_out set out_status='4' where id=#{id}")
    void gotoStopDrugOutById(DrugOut drugOut);

}