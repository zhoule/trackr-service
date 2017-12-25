package com.xplusz.trackr.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

@TypeAlias("HolidayPolicy") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class HolidayPolicy extends BaseEntity {

    String name;

    String company;

    List<String> users;


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.company, "name");
        put(map, this.company, "company");
        put(map, this.company, "users");
        return map;
    }

}
