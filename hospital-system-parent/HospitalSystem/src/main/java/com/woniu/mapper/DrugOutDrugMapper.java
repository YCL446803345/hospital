package com.woniu.mapper;

import com.woniu.entity.DrugOutDrug;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugOutDrugMapper {
    //新增退药药品中间表数据
    @Insert("insert into HOS_drug_out_drug(drug_id,drug_out_id,num) values(#{drugId},#{drugOutId},#{num})")
    void addDrugOutDrug(DrugOutDrug drugOutDrug);

    @Select("select d.id ddid,d.name drugName,dod.num num " +
            "from HOS_drug_out_drug dod,HOS_drug d " +
            "where dod.drug_id=d.id and dod.drug_out_id=#{pid}")
    List<DrugOutDrug> getByIdDrugs(Integer pid);

    //撤销退药
    @Delete("delete from HOS_drug_out_drug  where drug_out_id=#{drugOutId}")
    void gotoStopDrugOutDrugById(DrugOutDrug drugOutDrug);
}
