package com.woniu.mapper;

import com.woniu.entity.Dept;
import com.woniu.entity.DeptExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptMapper {
    long countByExample(DeptExample example);

    int deleteByExample(DeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dept record);

    int insertSelective(Dept record);


    List<Dept> selectByExample(DeptExample example);

    @Select("\n" +
            "select * from HOS_dept where name!='人事部' and name!='财务部'\n" +
            "\t\t")
    List<Dept> select2();

    Dept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);

    int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}