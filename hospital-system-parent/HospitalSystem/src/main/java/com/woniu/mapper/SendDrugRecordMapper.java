package com.woniu.mapper;

import com.woniu.entity.SendDrugRecord;
import com.woniu.entity.SendDrugRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}