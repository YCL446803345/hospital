package com.woniu.entity;

public class WorkerPerms {
    private Integer workerId;

    private Integer permsId;

    private String spare1;

    private String spare2;

    private String spare3;

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getPermsId() {
        return permsId;
    }

    public void setPermsId(Integer permsId) {
        this.permsId = permsId;
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