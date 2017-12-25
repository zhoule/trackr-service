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

@TypeAlias("Claim") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Claim").
public class Claim extends BaseEntity {

    /**
     * The name of the Claim.
     */
    private String claimName;

    /**
     * Who submit the claim.
     */
    private String userId;

    /**
     * What date.
     */
    private String date;

    /**
     * The status of the claim
     *  {
     *      "OPEN",
     *      "PROCESSING",
     *      "APPROVED",
     *      "REJECTED"
     *  }
     */
    private String status;

    /**
     * A list of expenses.
     */
    private Collection<Expense> expenses;

    private String company;

    public Collection<Expense> addExpense(final Expense expense) {
        if (getExpenses() == null) {
            setExpenses(new ArrayList<>());
        }
        getExpenses().add(expense);
        return getExpenses();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.claimName, "claimName");
        put(map, this.userId, "userId");
        put(map, this.date, "date");
        put(map, this.status, "status");
        put(map, this.expenses, "expenses");
        put(map, this.company, "company");
        return map;
    }
}
