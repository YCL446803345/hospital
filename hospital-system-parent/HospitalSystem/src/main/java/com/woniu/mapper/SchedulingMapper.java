package com.woniu.mapper;

import com.woniu.entity.Scheduling;
import com.woniu.entity.SchedulingExample;
import com.woniu.entity.Worker;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SchedulingMapper {
    long countByExample(SchedulingExample example);

    int deleteByExample(SchedulingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Scheduling record);

    int insertSelective(Scheduling record);

    List<Scheduling> selectByExample(SchedulingExample example);

    Scheduling selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByExample(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByPrimaryKeySelective(Scheduling record);

    int updateByPrimaryKey(Scheduling record);
    @Select("select * from HOS_scheduling ")
    List<Scheduling> select(SchedulingExample schedulingExample);



    @Update("update HOS_worker SET spare1 = #{spare1} WHERE id = #{id} ")
    void update(Scheduling scheduling);

    @Select("select id,worktime,remarks from HOS_scheduling")
    List<Scheduling> findSchedulings();

    @Select(" select count(*) from HOS_worker w\n" +
            " left join HOS_worker_scheduling ws on ws.worker_id=w.id\n" +
            " left join HOS_scheduling s on ws.scheduling_id=s.id\n" +
            " where w.id=#{id} and ws.time=#{time} and s.id=#{schedulingId}")
    Integer getWorkerNumBySchedulingTimeAndWorkId(Worker worker);

    @Insert("insert into HOS_worker_scheduling(worker_id,scheduling_id,time) values " +
            "(#{id},#{schedulingId},#{time})")
    void scheduling(Worker worker);

    @Select("select s.id id,w.name workerName,s.id schedulingId,r.name roleName from HOS_worker w\n" +
            " left join HOS_worker_scheduling ws on ws.worker_id=w.id\n" +
            " left join HOS_scheduling s on ws.scheduling_id=s.id\n" +
            " left join HOS_role r on w.dept_id=r.id\n" +
            " where ws.time=#{time}")
    List<Scheduling> findWorkersBySchedulingTime(String time);

    @Select("select w.name workerName,s.id schedulingId, r.name roleName,ws.time time from HOS_worker w\n" +
            " left join HOS_worker_scheduling ws on ws.worker_id=w.id\n" +
            " left join HOS_scheduling s on ws.scheduling_id=s.id\n" +
            " left join HOS_role r on w.dept_id=r.id\n" +
            " where ws.time is not null")
    List<Scheduling> findAllWorkersScheduling();
}