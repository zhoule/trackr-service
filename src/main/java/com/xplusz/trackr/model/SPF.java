package com.xplusz.trackr.model;

public class SPF {
    //Todo user should be able change the base pay in configuration page.
    private double MIN_BASE_PAY_SPF = 2697;

    private double MAX_BASE_PAY_SPF = 18245;

    //Tod need to figure out how to calculate the SPF if user's spf type is B and base salary is bigger than 18100
    private double MAX_BASE_PAY_SPF_B_HOUSING = 18100;

    private double SPF_PERSONAL_RATE = 0.185;
    private double SPF_COMPANY_RATE = 0.274;

    private double baseSalary;

    private double minBasePaySPF;

    private double maxBasePaySPF;

    private double personalSPF;

    private double companySPF;

    private String type;

    public SPF(){}

    public SPF(double salary, String type) {
        this.baseSalary = salary;
        this.type = type;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getMinBasePaySPF() {
        return MIN_BASE_PAY_SPF;
    }

    public double getMaxBasePaySPF() {
        return MAX_BASE_PAY_SPF;
    }

    public double getPersonalSPF() {
        return getBasePay() * SPF_PERSONAL_RATE;
    }

    public void setPersonalSPF(double personalSPF) {
        this.personalSPF = personalSPF;
    }

    public double getCompanySPF() {
        return getBasePay() * SPF_COMPANY_RATE;
    }

    public void setCompanySPF(double companySPF) {
        this.companySPF = companySPF;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private double getBasePay() {
        double basePay = this.baseSalary;
        if(this.baseSalary < getMinBasePaySPF()) {
            basePay = getMinBasePaySPF();
        }else if(this.baseSalary > getMaxBasePaySPF()) {
            basePay = getMaxBasePaySPF();
        }
        return basePay;
    }
}
