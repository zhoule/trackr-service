package com.xplusz.trackr.services;

import com.xplusz.trackr.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClaimService {

    Claim findByPrimaryKey(String id);

    Page<Claim> getClaimByCompany(String company, Pageable pageable);

    Page<Claim> getClaimByUser(String user, Pageable pageable);

    Claim save(Claim input);

    Claim update(final String claimId, Claim input);

    void delete(final String claimId);
}
