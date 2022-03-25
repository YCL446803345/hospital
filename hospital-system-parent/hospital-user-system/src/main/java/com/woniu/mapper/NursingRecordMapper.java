package com.woniu.mapper;

import com.woniu.entity.NursingRecord;
import com.woniu.entity.NursingRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NursingRecordMapper {
    long countByExample(NursingRecordExample example);

    int deleteByExample(NursingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NursingRecord record);

    int insertSelective(NursingRecord record);

    List<NursingRecord> selectByExample(NursingRecordExample example);

    NursingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NursingRecord record, @Param("example") NursingRecordExample example);

    int updateByExample(@Param("record") NursingRecord record, @Param("example") NursingRecordExample example);

    int updateByPrimaryKeySelective(NursingRecord record);

    int updateByPrimaryKey(NursingRecord record);
}