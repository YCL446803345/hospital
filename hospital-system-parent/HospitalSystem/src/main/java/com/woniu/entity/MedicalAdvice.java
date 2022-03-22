package com.woniu.entity;

import java.util.Date;

public class MedicalAdvice {
    private Integer id;

    private Integer doctorId;

    private Integer patientId;

    private Integer adviceCategory;

    private Integer projectId;

    private Date createTime;

    private String adviceDescription;

    private Integer adviceStatus;

    private String spare1;

    private String spare2;

    private String spare3;

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

    public Integer getAdviceCategory() {
        return adviceCategory;
    }

    public void setAdviceCategory(Integer adviceCategory) {
        this.adviceCategory = adviceCategory;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAdviceDescription() {
        return adviceDescription;
    }

    public void setAdviceDescription(String adviceDescription) {
        this.adviceDescription = adviceDescription == null ? null : adviceDescription.trim();
    }

    public Integer getAdviceStatus() {
        return adviceStatus;
    }

    public void setAdviceStatus(Integer adviceStatus) {
        this.adviceStatus = adviceStatus;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2 == null ? null : spare2.trim();
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3 == null ? null : spare3.trim();
    }
}