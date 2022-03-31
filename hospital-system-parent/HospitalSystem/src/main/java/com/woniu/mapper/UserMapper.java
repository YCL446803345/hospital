package com.woniu.mapper;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import java.util.List;

import com.woniu.entity.WorkerScheduling;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(User record);
    int insertSelective(User record);

    List<User> selectByExample(UserExample example);
    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);

    @Select("select ws.scheduling_id SchedulingId,ws.time\n" +
            "from HOS_worker_scheduling ws,HOS_worker w\n" +
            "where ws.worker_id = w.id and w.account = #{account} ")
    List<WorkerScheduling> getMyScheduling (String account);
}