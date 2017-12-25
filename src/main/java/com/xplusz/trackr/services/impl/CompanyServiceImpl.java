package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.Company;
import com.xplusz.trackr.repository.CompanyRepository;
import com.xplusz.trackr.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by hank on 4/10/17.
 */
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, MongoOperations mongoOperations) {
        super(mongoOperations);
        this.companyRepository = companyRepository;
    }

    @Override
    public  Company findByPrimaryKey(final String primaryKey){
        Optional<Company> company = companyRepository.findByPrimaryKey(primaryKey);
        return company.orElseThrow(() -> new EntityNotFoundException("Company"));
    }

    @Override
    public Company findByCompany(String company){
        return companyRepository.findByCompanyName(company);
    }

    @Override
    public Company save(Company input) {
        return companyRepository.save(input);
    }

    @Override
    public Company update(String companyId, Company input) {
        return (Company) super.update(companyId, input);
    }

    @Override
    public void delete(String companyId) {
        companyRepository.delete(companyId);
    }
}
