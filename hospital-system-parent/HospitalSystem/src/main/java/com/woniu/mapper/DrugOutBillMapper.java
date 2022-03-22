package com.woniu.mapper;

import com.woniu.entity.DrugOutBill;
import com.woniu.entity.DrugOutBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugOutBillMapper {
    long countByExample(DrugOutBillExample example);

    int deleteByExample(DrugOutBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrugOutBill record);

    int insertSelective(DrugOutBill record);

    List<DrugOutBill> selectByExample(DrugOutBillExample example);

    DrugOutBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrugOutBill record, @Param("example") DrugOutBillExample example);

    int updateByExample(@Param("record") DrugOutBill record, @Param("example") DrugOutBillExample example);

    int updateByPrimaryKeySelective(DrugOutBill record);

    int updateByPrimaryKey(DrugOutBill record);

    @Select("select dob.money\n" +
            "from HOS_patient p,HOS_drug_out dou,HOS_drugout_bill dob\n" +
            "where  dou.id = dob.drugoutapply_id and p.id=dou.patient_id and p.id=#{id} and dob.status=1")
    List<Float> selectBillMoneyByPatientId (Integer id);

    @Update("update \n" +
            " HOS_patient p,HOS_drug_out dou,HOS_drugout_bill dob\n" +
            " set dob.`status` = 2\n" +
            " where dou.id = dob.drugoutapply_id and p.id=dou.patient_id and p.id=#{id}")
    void updateStatus (Integer id);

    @Update("update \n" +
            " HOS_patient p,HOS_drug_out dou,HOS_drugout_bill dob\n" +
            " set dob.spare1 = #{newTime}\n" +
            " where dou.id = dob.drugoutapply_id and p.id=dou.patient_id and p.id=#{id}\n" +
            " ")
    void updateDate (@Param("newTime") String nowTime,@Param("id")Integer id);
}