package com.woniu.mapper;

import com.woniu.entity.Perms;
import com.woniu.entity.PermsExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
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

    //细粒度
    @Select("select p.* from HOS_perms p inner join HOS_worker_perms wp on p.id=wp.perms_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_worker w on w.id=wp.worker_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\twhere w.account=#{account} and type='m'")
    List<Perms> byNamefindPerms(String account);


    //粗粒度
//    @Select("select p.* from HOS_perms p \n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_role_perm rp on p.id=rp.perm_id\n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_role_worker rw on rw.role_id=rp.role_id\n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_worker w on w.id=rw.worker_id\n" +
//            "\t\t\t\t\t\t\t\t\t\t\t\t\twhere w.account=#{account} and type='m'")
//    List<Perms> byNamefindPerms(String account);


    @Select("select perms_id from HOS_worker_perms where worker_id=#{id}")
    List<String> getPermByUserId(Integer id);

    @Delete("delete from HOS_worker_perms where worker_id=#{id}")
    void deletePermsByUserid(Integer id);

    @Insert("insert into HOS_worker_perms(worker_id,perms_id)values(#{userId},#{permsId})")
    void updatePerms(@Param("userId") Integer userId, @Param("permsId") Integer  permsId);

    @Select("SELECT * FROM HOS_perms")
    List<Perms> findAllPerms();

}