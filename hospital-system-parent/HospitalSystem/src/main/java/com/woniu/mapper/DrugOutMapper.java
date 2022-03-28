package com.woniu.mapper;

import com.woniu.entity.DrugOut;
import com.woniu.entity.DrugOutExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
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
    //分页模糊查询退药列表
    List<DrugOut> queryDrugOutList(DrugOut drugOut);

    @Insert("insert into HOS_drug_out(doctor_id,patient_id,out_status,create_time,out_cause) values(#{doctorId},#{patientId},#{outStatus},#{createTime},#{outCause})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addDrugOut(DrugOut drugOut);
    //撤销退药
    @Update("update HOS_drug_out set out_status='4' where id=#{id}")
    void gotoStopDrugOutById(DrugOut drugOut);
}