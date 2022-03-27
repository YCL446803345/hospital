package com.woniu.mapper;

import com.woniu.entity.Purchase;
import com.woniu.entity.PurchaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseMapper {

    long countByExample(PurchaseExample example);

    int deleteByExample(PurchaseExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(Purchase record);
    int insertSelective(Purchase record);

    List<Purchase> selectByExample(PurchaseExample example);
    Purchase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Purchase record, @Param("example") PurchaseExample example);
    int updateByExample(@Param("record") Purchase record, @Param("example") PurchaseExample example);
    int updateByPrimaryKeySelective(Purchase record);
    int updateByPrimaryKey(Purchase record);
}