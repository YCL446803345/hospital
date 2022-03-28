package com.woniu.mapper;

import com.woniu.entity.InHospitalTable;
import com.woniu.entity.InHospitalTableExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //审核预约住院信息
    @Update("update HOS_in_hospital_table set status='2' where id=#{id}")
    void updateInHospitalTable(InHospitalTable inHospitalTable);

    //根据手机号码查询病人预约情况
    @Select("select i.*,b.code bedCode,d.name deptName from HOS_in_hospital_table i,HOS_bed b,HOS_dept d where i.bed_id=b.id and i.dept_id=d.id and telephone=#{telephone}")
    List<InHospitalTable> selectByExample2(InHospitalTableExample inHospitalTableExample);
    //根据手机号码查询病人预约情况
    @Select("select i.*,b.code bedCode,d.name deptName from HOS_in_hospital_table i,HOS_bed b,HOS_dept d where i.bed_id=b.id and i.dept_id=d.id and telephone=#{telephone}")
    List<InHospitalTable> getInHospitalTableByTelephone(String telephone);
}