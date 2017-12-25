package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

@TypeAlias("HolidayBalances") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class HolidayBalances extends BaseEntity{

    String username;

    String company;

    String type;

    String year;

    int count;


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.company, "company");
        put(map, this.company, "username");
        put(map, this.company, "type");
        put(map, this.company, "year");
        put(map, this.company, "count");
        return map;
    }

}
