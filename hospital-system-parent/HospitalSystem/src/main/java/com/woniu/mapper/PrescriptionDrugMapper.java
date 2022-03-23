package com.woniu.mapper;

import com.woniu.entity.PrescriptionDrug;
import com.woniu.entity.PrescriptionDrugExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDrugMapper {

    long countByExample(PrescriptionDrugExample example);

    int deleteByExample(PrescriptionDrugExample example);

    int insert(PrescriptionDrug record);
    int insertSelective(PrescriptionDrug record);

    List<PrescriptionDrug> selectByExample(PrescriptionDrugExample example);

    int updateByExampleSelective(@Param("record") PrescriptionDrug record, @Param("example") PrescriptionDrugExample example);
    int updateByExample(@Param("record") PrescriptionDrug record, @Param("example") PrescriptionDrugExample example);

    @Select("select d.name drugName,pd.num " +
            "from HOS_prescription_drug pd,HOS_drug d " +
            "where pd.drug_id=d.id and pd.prescription_id=#{pid}")
    List<PrescriptionDrug> getDrugNameAndNumByPreId(Integer pid);
}