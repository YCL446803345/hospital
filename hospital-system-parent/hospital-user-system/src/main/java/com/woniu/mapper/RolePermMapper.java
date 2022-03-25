package com.woniu.mapper;

import com.woniu.entity.RolePerm;
import com.woniu.entity.RolePermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermMapper {
    long countByExample(RolePermExample example);

    int deleteByExample(RolePermExample example);

    int insert(RolePerm record);

    int insertSelective(RolePerm record);

    List<RolePerm> selectByExample(RolePermExample example);

    int updateByExampleSelective(@Param("record") RolePerm record, @Param("example") RolePermExample example);

    int updateByExample(@Param("record") RolePerm record, @Param("example") RolePermExample example);
}