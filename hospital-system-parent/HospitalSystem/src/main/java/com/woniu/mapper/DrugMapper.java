package com.woniu.mapper;

import com.woniu.entity.Drug;
import com.woniu.entity.DrugExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;
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

    @Select("  select DISTINCT d.name,d.sale_price,pd.num inNum\n" +
            " from HOS_prescription pr, HOS_prescription_drug pd, HOS_drug d,HOS_patient p,HOS_prescription_bill pb\n" +
            " where pr.patient_id = p.id and pr.id = pd.prescription_id and pd.drug_id = d.id and p.id = #{id} and pb.status = 1 and pr.prescription_status = 4" )
    List<Drug> selectDrugByPatientId(Integer id);

    @Select("  select d.name,d.sale_price,dod.num outNum\n" +
            " from HOS_drug d,HOS_patient p,HOS_drug_out do,HOS_drugout_bill db,HOS_drug_out_drug dod\n" +
            " where  db.status = 1 and p.id = do.patient_id and do.id = dod.drug_out_id and dod.drug_id = d.id and db.drugoutapply_id = do.id and p.id = #{id}")
    List<Drug> selectByPatientId (int id);

    @Update("update HOS_drug d set d.stock=#{newStock} where d.name=#{drugName}")
    void updateStock(@Param("newStock") Integer newStock, @Param("drugName") String drugName);


    @Select("select drug_type from HOS_drug")
    List<String> findAllDrugType();

    void updateByIdBatchStatus(List<Integer> ids);

    @Select("select d.name name,d.drug_type drugType,d.make_price makePrice,d.sale_price salePrice,\n" +
            "d.specifications specifications,d.spare1 spare1,pd.num num  \n" +
            "from HOS_prescription p\n" +
            "left join HOS_prescription_drug pd on p.id=pd.prescription_id\n" +
            "left join HOS_drug d on d.id=pd.drug_id\n" +
            "where p.id=#{id}")
    List<Drug> getDrugsByPrescriptionId(Integer id);

    @Update("update HOS_drug set stock=#{stock} where id=#{id}")
    void addDrugStockById(@Param("id") Integer id, @Param("stock") Integer stock);

    @Select("select sale_price from HOS_drug where id=#{id}")
    Float findPriceById(Drug drug);
}