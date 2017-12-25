package com.xplusz.trackr.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

@Getter
@Setter
@Document
public abstract class BaseEntity extends ResourceSupport{

    @Id
    private String primaryKey;

    @Version
    private Long version;

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public abstract Map<String, Object> toMap();

    protected void put(Map<String, Object> map, Object field, String fieldName) {
        if (field != null) {
            map.put(fieldName, field);
        }
    }
}



