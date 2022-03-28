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

    @Select("<script>" +"select w.*,r.name roleName,d.name deptName from\n" +
            "    HOS_worker w,HOS_dept d,HOS_role r\n" +
            "    where w.dept_id=d.id and w.role_id=r.id  \n"+
            "    <if test=\"name != null and name != ''\">\n" +
            "      and w.name  like  '%${name}%'\n" +
            "    </if>\n" +
            "  <if test=\"deptName != null and deptName != ''\">\n" +
            "      and d.name=#{deptName}\n" +
            "    </if>\n" +
            "  <if test=\"roleName != null and roleName != ''\">\n" +
            "      and r.name=#{roleName}\n" +
            "    </if>" +
            "</script>")
    List<Worker> select(Worker worker);

    @Select("select name from HOS_worker where account=#{account}")
    Worker selectWorkerByAccount(String account);

    @Select("select name from HOS_worker where role_id=5")
    List<String> findAllDoctor();
}