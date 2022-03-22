package com.woniu.mapper;

import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerMapper {
    long countByExample(WorkerExample example);

    int deleteByExample(WorkerExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(Worker record);
    int insertSelective(Worker record);

    List<Worker> selectByExample(WorkerExample example);
    Worker selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Worker record, @Param("example") WorkerExample example);
    int updateByExample(@Param("record") Worker record, @Param("example") WorkerExample example);
    int updateByPrimaryKeySelective(Worker record);
    int updateByPrimaryKey(Worker record);

    @Select("select p.percode from HOS_perms p inner join HOS_worker_perms wp on(p.id=wp.perms_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_worker w on(w.id=wp.worker_id) where w.account=#{account}")
    List<String> selectPercodeByPerm(@Param("account") String account);

    @Select("select w.*, r.name roleName, d.name deptName\n" +
            "        from HOS_worker w\n" +
            "                 inner join HOS_role r on w.role_id = r.id\n" +
            "                 inner join HOS_dept d on w.dept_id = d.id")
    List<Worker> select(WorkerExample example);
}