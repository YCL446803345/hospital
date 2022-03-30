package com.woniu.mapper;

import com.woniu.entity.Perms;
import com.woniu.entity.PermsExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermsMapper {
    long countByExample(PermsExample example);

    int deleteByExample(PermsExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(Perms record);
    int insertSelective(Perms record);

    List<Perms> selectByExample(PermsExample example);
    Perms selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Perms record, @Param("example") PermsExample example);
    int updateByExample(@Param("record") Perms record, @Param("example") PermsExample example);
    int updateByPrimaryKeySelective(Perms record);
    int updateByPrimaryKey(Perms record);

    //    //细粒度
//    @Select("select p.* from HOS_perms p inner join HOS_worker_perms wp on p.id=wp.perms_id \n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_worker w on w.id=wp.worker_id\n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\t\twhere w.account=#{account} and type='m'")
//    List<Perms> byNamefindPerms(String account);


    //粗粒度
    @Select("select p.* from HOS_perms p \n" +
            "inner join HOS_role_perm rp on p.id=rp.perm_id\n" +
            "inner join HOS_role r on r.id=rp.role_id\n" +
            "inner join HOS_worker w on r.id=w.role_id\n" +
            "where w.account=#{account} and type='m'")
    List<Perms> byNamefindPerms(String account);

    //根据用户角色获取权限树
    @Select("select perm_id from HOS_role_perm where role_id=#{id}")
    List<String> getPermByRoleId(Integer id);

    //删除权限
    @Delete("delete from HOS_role_perm where role_id=#{id}")
    void deletePermsByRoleId(Integer id);

    //修改权限
    @Insert("insert into HOS_role_perm(role_id,perm_id)values(#{roleId},#{permsId})")
    void updatePerms(@Param("roleId") Integer roleId, @Param("permsId") Integer  permsId);

    //查询所有权限
    @Select("SELECT * FROM HOS_perms")
    List<Perms> findAllPerms();

}