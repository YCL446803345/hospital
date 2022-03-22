package com.woniu.entity;

import java.util.Date;

public class ConsultationApplication {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    private Date consultationDate;

    private String reason;

    private String desc;

    private Integer consultationEmergencyId;

    private Integer consultationCategoryId;

    private String status;

    private String spare3;

    private String spare2;

    private String spare1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(Date consultationDate) {
        this.consultationDate = consultationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getConsultationEmergencyId() {
        return consultationEmergencyId;
    }

    public void setConsultationEmergencyId(Integer consultationEmergencyId) {
        this.consultationEmergencyId = consultationEmergencyId;
    }

    public Integer getConsultationCategoryId() {
        return consultationCategoryId;
    }

    public void setConsultationCategoryId(Integer consultationCategoryId) {
        this.consultationCategoryId = consultationCategoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3 == null ? null : spare3.trim();
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2 == null ? null : spare2.trim();
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }
}