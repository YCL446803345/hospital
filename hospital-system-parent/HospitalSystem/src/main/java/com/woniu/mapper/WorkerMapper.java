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

    @Select("select p.percode from HOS_perms p inner join HOS_role_perm rp on(p.id=rp.perm_id)inner join HOS_worker w on(w.role_id=rp.role_id) where w.account=#{account}")
    List<String> selectPercodeByPerm(@Param("account") String account);

    @Select("<script>" +"select w.*,r.name roleName,d.name deptName,s.worktime shift from\n" +
            "    HOS_worker w,HOS_dept d,HOS_role r,HOS_scheduling s\n" +
            "    where w.dept_id=d.id and w.role_id=r.id and w.spare1=s.id order by w.id asc \n"+
            "    <if test=\"name != null and name != ''\">\n" +
            "      and w.name  like  '%${name}%'\n" +
            "    </if>\n" +
            "  <if test=\"deptName != null and deptName != ''\">\n" +
            "      and d.name=#{deptName}\n" +
            "    </if>\n" +
            "  <if test=\"shift != null and shift != ''\">\n" +
            "      and s.worktime=#{shift}\n" +
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