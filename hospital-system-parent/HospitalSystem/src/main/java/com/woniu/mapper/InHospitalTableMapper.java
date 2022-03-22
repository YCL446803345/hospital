package com.woniu.mapper;

import com.woniu.entity.InHospitalTable;
import com.woniu.entity.InHospitalTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InHospitalTableMapper {
    long countByExample(InHospitalTableExample example);

    int deleteByExample(InHospitalTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InHospitalTable record);

    int insertSelective(InHospitalTable record);

    List<InHospitalTable> selectByExample(InHospitalTableExample example);

    InHospitalTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InHospitalTable record, @Param("example") InHospitalTableExample example);

    int updateByExample(@Param("record") InHospitalTable record, @Param("example") InHospitalTableExample example);

    int updateByPrimaryKeySelective(InHospitalTable record);

    int updateByPrimaryKey(InHospitalTable record);

    //医生站分页模糊查询住院申请列表
    List<InHospitalTable> queryInHospitalTableList(InHospitalTable inHospitalTable);

}