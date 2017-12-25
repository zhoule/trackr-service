package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@TypeAlias("Expense") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "com.xplusz.trackr.model.Expense").
public class Expense extends BaseEntity {

    /**
     * Who add the expense.
     */
    private String userId;

    /**
     * When user add the expense.
     */
    private String date;

    /**
     * The category of expense.
     */
    private String category;

    /**
     * The amount of the expense.
     */
    private double amount;

    /**
     * User comments of the expense.
     */
    private String comments;

    /**
     * The receipt of the expense, this should be the image of te receipt.
     */
    private String receipt;

    /**
     * The expense was include which claim.
     */
    private String claimId;

    /**
     * The status of the expense.
     * {
     *     "Unclaimed",
     *     "Unsubmitted",
     *     "Processing"
     * }
     */
    private String status;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        put(map, this.userId, "userId");
        put(map, this.date, "date");
        put(map, this.category, "category");
        put(map, this.amount, "amount");
        put(map, this.comments, "comments");
        put(map, this.receipt, "receipt");
        put(map, this.claimId, "claimId");
        put(map, this.status, "status");
        return map;
    }
}
