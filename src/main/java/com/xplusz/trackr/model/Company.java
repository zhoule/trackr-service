package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hank on 4/10/17.
 */
@Getter
@Setter
@TypeAlias("company")
public class Company extends BaseEntity {
    /**
     * The name of the company.
     */
    private String companyName;


    private String userId;

    /**
     * What date.
     */
    private String date;

    private String status;

    private String[] modules;



    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.companyName, "companyName");
        put(map, this.userId, "userId");
        put(map, this.date, "date");
        put(map, this.status, "status");
        put(map, this.modules, "modules");
        return map;
    }
}
