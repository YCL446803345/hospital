package com.woniu.mapper;

import com.woniu.entity.PaymentRecord;
import com.woniu.entity.PaymentRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordMapper {
    long countByExample(PaymentRecordExample example);

    int deleteByExample(PaymentRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PaymentRecord record);

    int insertSelective(PaymentRecord record);

    List<PaymentRecord> selectByExample(PaymentRecordExample example);

    PaymentRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PaymentRecord record, @Param("example") PaymentRecordExample example);

    int updateByExample(@Param("record") PaymentRecord record, @Param("example") PaymentRecordExample example);

    int updateByPrimaryKeySelective(PaymentRecord record);

    int updateByPrimaryKey(PaymentRecord record);
}