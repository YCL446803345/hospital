package com.woniu.mapper;

import com.woniu.entity.PrescriptionBill;
import com.woniu.entity.PrescriptionBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionBillMapper {
    long countByExample(PrescriptionBillExample example);

    int deleteByExample(PrescriptionBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrescriptionBill record);

    int insertSelective(PrescriptionBill record);

    List<PrescriptionBill> selectByExample(PrescriptionBillExample example);

    PrescriptionBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrescriptionBill record, @Param("example") PrescriptionBillExample example);

    int updateByExample(@Param("record") PrescriptionBill record, @Param("example") PrescriptionBillExample example);

    int updateByPrimaryKeySelective(PrescriptionBill record);

    int updateByPrimaryKey(PrescriptionBill record);
}