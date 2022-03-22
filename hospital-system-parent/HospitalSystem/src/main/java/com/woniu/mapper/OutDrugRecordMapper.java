package com.woniu.mapper;

import com.woniu.entity.OutDrugRecord;
import com.woniu.entity.OutDrugRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OutDrugRecordMapper {
    long countByExample(OutDrugRecordExample example);

    int deleteByExample(OutDrugRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OutDrugRecord record);

    int insertSelective(OutDrugRecord record);

    List<OutDrugRecord> selectByExample(OutDrugRecordExample example);

    OutDrugRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OutDrugRecord record, @Param("example") OutDrugRecordExample example);

    int updateByExample(@Param("record") OutDrugRecord record, @Param("example") OutDrugRecordExample example);

    int updateByPrimaryKeySelective(OutDrugRecord record);

    int updateByPrimaryKey(OutDrugRecord record);
}