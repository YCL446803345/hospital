package com.woniu.mapper;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //根据手机号码查询对应权限
    @Select("select p.percode from HOS_perms p inner join HOS_user_perms up on(p.id=up.perms_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tinner join HOS_user u on(u.id=up.user_id) where u.telephone=#{telephone}")
    List<String> selectPercodeByPerm(String telephone);
}