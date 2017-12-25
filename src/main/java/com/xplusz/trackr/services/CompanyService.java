package com.xplusz.trackr.services;

import com.xplusz.trackr.model.Company;

/**
 * Created by hank on 4/10/17.
 */
public interface CompanyService {

    Company findByPrimaryKey(final String primaryKey);

    Company findByCompany(String company);

    Company save(Company input);

    Company update(final String companyId, Company input);

    void delete(final String companyId);
}
