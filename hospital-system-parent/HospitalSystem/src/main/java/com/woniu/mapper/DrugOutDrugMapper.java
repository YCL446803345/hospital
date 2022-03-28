package com.woniu.mapper;

import com.woniu.entity.DrugOutDrug;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugOutDrugMapper {
    //新增退药药品中间表数据
    @Insert("insert into HOS_drug_out_drug(drug_id,drug_out_id,num) values(#{drugId},#{drugOutId},#{num})")
    void addDrugOutDrug(DrugOutDrug drugOutDrug);
}
