package com.xplusz.trackr.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

@TypeAlias("Workdays") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class Workdays extends BaseEntity  {

    boolean monday;

    boolean tuesday;

    boolean wednesday;

    boolean thursday;

    boolean friday;

    boolean saturday;

    boolean sunday;

    int hoursPerDay;

    String company;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.company, "monday");
        put(map, this.company, "tuesday");
        put(map, this.company, "wednesday");
        put(map, this.company, "thursday");
        put(map, this.company, "friday");
        put(map, this.company, "saturday");
        put(map, this.company, "sunday");
        put(map, this.company, "hoursPerDay");
        put(map, this.company, "company");
        return map;
    }
}
