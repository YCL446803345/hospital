package com.woniu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cost {
    private Float drugOut;
    private Float prescription;
    private Float hospitalization;
    private Float medicalAdvice;
    private Float balance;
    private Patient patient;
    private List<Drug> drugOutList;
    private List<Drug> drugInList;
    private Integer days;
    private List<Project> projectList;

    public Cost (Float drugOut, Float prescription, Float hospitalization, Float medicalAdvice, Float balance,Integer days) {
        this.drugOut = drugOut;
        this.prescription = prescription;
        this.hospitalization = hospitalization;
        this.medicalAdvice = medicalAdvice;
        this.balance = balance;
        this.days = days;
    }

    public Cost (Float drugOut, Float prescription, Float hospitalization, Float medicalAdvice, Float balance, Patient patient) {
        this.drugOut = drugOut;
        this.prescription = prescription;
        this.hospitalization = hospitalization;
        this.medicalAdvice = medicalAdvice;
        this.balance = balance;
        this.patient = patient;
    }
}
