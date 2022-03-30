package com.woniu.mapper;

import com.woniu.entity.WithdrawalDrug;
import com.woniu.entity.WithdrawalDrugExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalDrugMapper {

    long countByExample(WithdrawalDrugExample example);

    int deleteByExample(WithdrawalDrugExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawalDrug record);
    int insertSelective(WithdrawalDrug record);

    List<WithdrawalDrug> selectByExample(WithdrawalDrugExample example);
    WithdrawalDrug selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WithdrawalDrug record, @Param("example") WithdrawalDrugExample example);
    int updateByExample(@Param("record") WithdrawalDrug record, @Param("example") WithdrawalDrugExample example);
    int updateByPrimaryKeySelective(WithdrawalDrug record);
    int updateByPrimaryKey(WithdrawalDrug record);

    @Select("<script>" +
            "select wd.spare1 spare1,do.id drugOutId, w.name doctorName,p.name preName,wd.out_drug_time outTime,do.out_cause outCause,wd.out_name outName\n" +
            "from HOS_withdrawal_drug wd\n" +
            "left join HOS_drug_out do on do.id=wd.drug_out_id\n" +
            "left join HOS_worker w on w.id=do.doctor_id\n" +
            "left join HOS_patient p on p.id=do.patient_id\n" +
            "where 1 = 1"+
            "<if test='name!=null and name != \"\"'>and wd.out_name like '%${name}%'</if>" +
            "</script>")
    List<WithdrawalDrug> findAllOutRecord(String name);
}