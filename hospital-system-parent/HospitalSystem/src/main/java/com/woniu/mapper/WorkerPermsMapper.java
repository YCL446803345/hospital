package com.woniu.mapper;

import com.woniu.entity.WorkerPerms;
import com.woniu.entity.WorkerPermsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerPermsMapper {
    long countByExample(WorkerPermsExample example);

    int deleteByExample(WorkerPermsExample example);

    int insert(WorkerPerms record);

    int insertSelective(WorkerPerms record);

    List<WorkerPerms> selectByExample(WorkerPermsExample example);

    int updateByExampleSelective(@Param("record") WorkerPerms record, @Param("example") WorkerPermsExample example);
    int updateByExample(@Param("record") WorkerPerms record, @Param("example") WorkerPermsExample example);

}