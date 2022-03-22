package com.woniu.mapper;

import com.woniu.entity.LeaveHospital;
import com.woniu.entity.LeaveHospitalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveHospitalMapper {
    long countByExample(LeaveHospitalExample example);

    int deleteByExample(LeaveHospitalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LeaveHospital record);

    int insertSelective(LeaveHospital record);

    List<LeaveHospital> selectByExample(LeaveHospitalExample example);

    LeaveHospital selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LeaveHospital record, @Param("example") LeaveHospitalExample example);

    int updateByExample(@Param("record") LeaveHospital record, @Param("example") LeaveHospitalExample example);

    int updateByPrimaryKeySelective(LeaveHospital record);

    int updateByPrimaryKey(LeaveHospital record);

    //医生站分页模糊查询出院申请列表
    List<LeaveHospital> queryLeaveHospitalList(LeaveHospital leaveHospital);
}