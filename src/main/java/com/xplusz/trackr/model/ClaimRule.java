package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@TypeAlias("ClaimRule") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.ClaimRule").
public class ClaimRule extends BaseEntity{

    private Collection<User> claimers;

    private Collection<User> approvers;

    private String company;

    public Collection<User> addClaimer(final User user) {
        if (getClaimers() == null) {
            setClaimers(new ArrayList<>());
        }
        getClaimers().add(user);
        return getClaimers();
    }

    public Collection<User> addApprover(final User user) {
        if (getApprovers() == null) {
            setApprovers(new ArrayList<>());
        }
        getApprovers().add(user);
        return getApprovers();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.claimers, "claimers");
        put(map, this.approvers, "approvers");
        put(map, this.company, "company");
        return map;
    }
}
