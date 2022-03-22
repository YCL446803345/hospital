package com.woniu.mapper;

import com.woniu.entity.HospitalizationBill;
import com.woniu.entity.HospitalizationBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HospitalizationBillMapper {
    long countByExample(HospitalizationBillExample example);

    int deleteByExample(HospitalizationBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HospitalizationBill record);

    int insertSelective(HospitalizationBill record);

    List<HospitalizationBill> selectByExample(HospitalizationBillExample example);

    HospitalizationBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HospitalizationBill record, @Param("example") HospitalizationBillExample example);

    int updateByExample(@Param("record") HospitalizationBill record, @Param("example") HospitalizationBillExample example);

    int updateByPrimaryKeySelective(HospitalizationBill record);

    int updateByPrimaryKey(HospitalizationBill record);
}