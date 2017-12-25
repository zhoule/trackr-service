package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@TypeAlias("Payroll") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Payroll").
public class Payroll extends BaseEntity{

    private String userName;

    private double baseSalary;

    private SPF spf;

    private double allowance;

    private double bonus;

    private double tax;

    private String comments;

    private double finalSalary;

    private double expenses;

    private double workingDays;

    private String month;

    private String company;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.userName, "userName");
        put(map, this.baseSalary, "baseSalary");
        put(map, this.spf, "spf");
        put(map, this.allowance, "allowance");
        put(map, this.bonus, "bonus");
        put(map, this.tax, "tax");
        put(map, this.comments, "comments");
        put(map, this.finalSalary, "expenses");
        put(map, this.workingDays, "workingDays");
        put(map, this.month, "month");
        put(map, this.company, "company");

        return map;
    }
}
