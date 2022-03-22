package com.woniu.mapper;

import com.woniu.entity.Drug;
import com.woniu.entity.DrugExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugMapper {

    long countByExample(DrugExample example);

    int deleteByExample(DrugExample example);
    int deleteByPrimaryKey(Integer id);

    int insert(Drug record);
    int insertSelective(Drug record);

    List<Drug> selectByExample(DrugExample example);
    Drug selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Drug record, @Param("example") DrugExample example);
    int updateByExample(@Param("record") Drug record, @Param("example") DrugExample example);
    int updateByPrimaryKeySelective(Drug record);
    int updateByPrimaryKey(Drug record);

    @Select("  select d.name,d.sale_price,pd.num inNum\n" +
            " from HOS_prescription pr, HOS_prescription_drug pd, HOS_drug d,HOS_patient p,HOS_prescription_bill pb\n" +
            " where pr.patient_id = p.id and pr.id = pd.prescription_id and pd.drug_id = d.id and p.id = #{id} and pb.status = 1")
    List<Drug> selectDrugByPatientId(Integer id);

    @Select("  select d.name,d.sale_price,odr.drug_num outNum\n" +
            " from HOS_out_drug_record odr, HOS_drug d,HOS_patient p,HOS_drug_out do,HOS_drugout_bill db\n" +
            " where p.no = odr.patient_no and odr.drug_name = d.name and p.id = #{id} and p.id = do.patient_id and do.id = db.drugoutapply_id and db.status = 1")
    List<Drug> selectByPatientId (int id);
}