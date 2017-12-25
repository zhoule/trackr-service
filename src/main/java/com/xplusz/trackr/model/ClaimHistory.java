package com.xplusz.trackr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimHistory {

    /**
     * The details of the claim
     */
    private Claim claim;

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
     * User comments of the claim.
     */
    private String comments;
}
