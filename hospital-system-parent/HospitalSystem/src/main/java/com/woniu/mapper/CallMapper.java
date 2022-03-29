package com.woniu.mapper;

import com.woniu.entity.Call;
import com.woniu.entity.CallExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CallMapper {
    long countByExample(CallExample example);

    int deleteByExample(CallExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Call record);

    int insertSelective(Call record);

    List<Call> selectByExample(CallExample example);

    Call selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Call record, @Param("example") CallExample example);

    int updateByExample(@Param("record") Call record, @Param("example") CallExample example);

    int updateByPrimaryKeySelective(Call record);

    int updateByPrimaryKey(Call record);

    @Select("<script>" +
            " select p.name patientName, w.name doctorName,c.*,de.name deptName\n" +
            " from HOS_patient p,HOS_call c,HOS_dept d,HOS_worker w,HOS_dept de\n" +
            " where c.dept_id = d.id and p.id = c.patient_id and w.id = p.doctor_id and de.id = p.dept_id and c.status = '1'" +
            "<if test='deptId!=null and deptId != \"\"'>and c.dept_id = #{deptId}</if>" +
            "<if test='appointmentTime!=null and appointmentTime != \"\"'>and c.appointment_time = #{appointmentTime}</if>" +
            "</script>")
    List<Call> selectByCall (@Param("deptId")Integer deptId, @Param("appointmentTime")String appointmentTime);
}