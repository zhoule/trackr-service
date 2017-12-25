package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.repository.ClaimRepository;
import com.xplusz.trackr.services.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClaimServiceImpl extends BaseService implements ClaimService {

    private final ClaimRepository claimRepository;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, MongoOperations mongoOperations) {
        super(mongoOperations);
        this.claimRepository = claimRepository;
    }

    @Override
    public Claim findByPrimaryKey(String id) {
        Optional<Claim> claim = claimRepository.findByPrimaryKey(id);
        return claim.orElseThrow(() -> new EntityNotFoundException("Claim"));
    }

    @Override
    public Page<Claim> getClaimByCompany(String company, Pageable pageable) {
        return claimRepository.findByCompany(company, pageable);
    }

    @Override
    public Page<Claim> getClaimByUser(String user, Pageable pageable) {
        return claimRepository.findByUserId(user, pageable);
    }

    @Override
    public Claim save(Claim input) {
        return claimRepository.save(input);
    }

    @Override
    public Claim update(String claimId, Claim input) {
        return (Claim) super.update(claimId, input);
    }

    @Override
    public void delete(String claimId) {
        claimRepository.delete(claimId);
    }
}
