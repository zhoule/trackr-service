package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

@TypeAlias("HolidayRecord") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Festival").
public class HolidayRecord  extends BaseEntity {

    String username;

    String company;

    String type;

    /**
     * Coming, In progress, finished.
     */
    String status;

    String description;

    List<String> dates;

    String year;


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.username, "username");
        put(map, this.type, "username");
        put(map, this.company, "company");
        put(map, this.status, "status");
        put(map, this.description, "description");
        put(map, this.dates, "dates");
        put(map, this.company, "year");
        return map;
    }
}
