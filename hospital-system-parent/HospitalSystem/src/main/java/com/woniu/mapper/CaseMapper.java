package com.woniu.mapper;

import com.woniu.entity.Case;
import com.woniu.entity.CaseExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseMapper {
    long countByExample(CaseExample example);

    int deleteByExample(CaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Case record);

    int insertSelective(Case record);

    List<Case> selectByExample(CaseExample example);

    Case selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Case record, @Param("example") CaseExample example);

    int updateByExample(@Param("record") Case record, @Param("example") CaseExample example);

    int updateByPrimaryKeySelective(Case record);

    int updateByPrimaryKey(Case record);

    //分页模糊查询病例列表
    List<Case> queryCaseList(Case c);
    //新增病例
    @Insert("insert into HOS_case(advice_id,case_description,create_time,spare1) values(#{adviceId},#{caseDescription},#{createTime},#{spare1})")
    void addCase(Case cases);
}