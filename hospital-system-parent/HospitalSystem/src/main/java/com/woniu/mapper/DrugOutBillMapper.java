package com.woniu.mapper;

import com.woniu.entity.DrugOutBill;
import com.woniu.entity.DrugOutBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}