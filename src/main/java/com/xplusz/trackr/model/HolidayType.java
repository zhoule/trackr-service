package com.xplusz.trackr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

@TypeAlias("HolidayType") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class HolidayType extends BaseEntity {

    private String typeName;

    private String description;

    private String company;

    private int allowance;

    private Date annualReset;

    private String carrayOver;

    private int waitingPeriod;


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.typeName, "typeName");
        put(map, this.description, "description");
        put(map, this.company, "company");
        put(map, this.allowance, "allowance");
        put(map, this.annualReset, "annualReset");
        put(map, this.carrayOver, "carrayOver");
        put(map, this.waitingPeriod, "waitingPeriod");
        return map;
    }
}
