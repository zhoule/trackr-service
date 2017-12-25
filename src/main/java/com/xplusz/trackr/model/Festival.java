package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

@TypeAlias("Festival") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class Festival extends BaseEntity {

    String company;

    String festivalName;

    String description;

    String startDate;

    String endDate;

    String year;

    //used to init festival
    boolean common;

    List<String> changes;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.company, "company");
        put(map, this.festivalName, "festivalName");
        put(map, this.description, "description");
        put(map, this.startDate, "startDate");
        put(map, this.year, "year");
        put(map, this.common, "common");
        put(map, this.changes, "changes");

        return map;
    }
}
