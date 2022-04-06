package com.woniu.mapper;

import com.woniu.entity.LeaveHospital;
import com.woniu.entity.LeaveHospitalExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
    //撤销出院
    @Update("update HOS_leave_hospital set leave_status = 1 where id=#{id}")
    void cancelLeaveHospitalById(Integer id);

    //申请出院
    @Insert("insert into HOS_leave_hospital(patient_id,doctor_id,leave_status,leave_time,leave_description) values(#{id},#{doctorId},#{leaveStatus},#{leaveTime},#{leaveDescription})")
    void addLeaveHospital(LeaveHospital leaveHospital);

    @Update("update HOS_leave_hospital set status=4 where patient_id=#{id}")
    void doOutHospital(Integer id);
}