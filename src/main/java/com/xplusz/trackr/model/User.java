package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@TypeAlias("User") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.User").
public class User extends BaseEntity implements Serializable {

    @NotNull(message = "Username can not be empty.")
    @Indexed(unique = true)
    private String userName;

    @NotNull(message = "Chinese name can not be empty.")
    private String chineseName;

    @NotNull(message = "Email can not be empty.")
    @Email(message = "Not a valid email.")
    private String email;

    private String password;

    private String avatar;

    private String phoneNumber;

    private String school;

    private String roles[];

    private String company;

    private Double salary;

    private String spfType;

    private String birthDay;

    private String status;

    private String joinDate;

    private String leavingDate;

    public User() {}

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.userName, "username");
        put(map, this.chineseName, "chineseName");
        put(map, this.email, "email");
        put(map, this.password, "password");
        put(map, this.avatar, "avatar");
        put(map, this.phoneNumber, "phoneNumber");
        put(map, this.school, "school");
        put(map, this.roles, "roles");
        put(map, this.company, "company");
        put(map, this.salary, "salary");
        put(map, this.spfType, "spfType");
        put(map, this.birthDay, "birthDay");
        put(map, this.status, "status");
        put(map, this.joinDate, "joinDate");
        put(map, this.leavingDate, "leavingDate");

        return map;
    }
}
