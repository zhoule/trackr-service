package com.xplusz.trackr.services;

import com.xplusz.trackr.model.Payroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PayrollService {

    Payroll findByPrimaryKey(String id);

    Payroll findByUserNameAndMonth(String userName, String month);

    Page<Payroll> findByCompanyAndMonth(String company, String month, Pageable pageable);

    Page<Payroll> getPayrollsByPage(Pageable pageable);

    Payroll generatePayrollByUser(String username, String month, boolean isOverride);

    List<Payroll> generatePayrollByCompany(String company, String month, boolean isOverride);

    Payroll save(Payroll input);

    Payroll update(final String payrollId, Payroll input);

    void delete(final String payrollId);

}
