package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.Payroll;
import com.xplusz.trackr.model.SPF;
import com.xplusz.trackr.model.User;
import com.xplusz.trackr.repository.PayrollRepository;
import com.xplusz.trackr.services.PayrollService;
import com.xplusz.trackr.services.UserService;
import com.xplusz.trackr.services.impl.UserServiceImpl;
import com.xplusz.trackr.utils.SalaryCalculator;
import com.xplusz.trackr.utils.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollServiceImpl extends BaseService implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final UserService userService;
    @Autowired
    public PayrollServiceImpl(
            PayrollRepository payrollRepository,
            UserService userService,
            MongoOperations mongoOperations
    ){
        super(mongoOperations);
        this.payrollRepository = payrollRepository;
        this.userService = userService;
    }


    @Override
    public Payroll findByPrimaryKey(String id) {
        Optional<Payroll> payroll = payrollRepository.findByPrimaryKey(id);
        return payroll.orElseThrow(() -> new EntityNotFoundException("Payroll"));
    }

    @Override
    public Payroll findByUserNameAndMonth(String userName, String month) {
        Optional<Payroll> payroll = payrollRepository.findByUserNameAndMonth(userName, month);
        return payroll.orElseThrow(() -> new EntityNotFoundException("Payroll"));
    }

    @Override
    public Page<Payroll> findByCompanyAndMonth(String company, String month, Pageable pageable) {
        return payrollRepository.findByCompanyAndMonth(company, month, pageable);
    }

    @Override
    public Page<Payroll> getPayrollsByPage(Pageable pageable) {
        return payrollRepository.findAll(pageable);
    }

    @Override
    public Payroll generatePayrollByUser(String username, String month, boolean isOverride) {
        Payroll payroll = generatePayroll(userService.findByUserName(username), month);
        return savePayroll(payroll, isOverride);
    }

    @Override
    public List<Payroll> generatePayrollByCompany(String company, String month, boolean isOverride) {
        List<Payroll> payrolls = new ArrayList<>();
        List<User> users = ((UserServiceImpl)userService).findByCompany(company);
        for(User user : users) {
            Payroll payroll = generatePayroll(user, month);
            payrolls.add(savePayroll(payroll, isOverride));
        }
        return payrolls;
    }

    @Override
    public Payroll save(Payroll input) {
        return payrollRepository.save(input);
    }

    @Override
    public Payroll update(String payrollId, Payroll input) {
        return (Payroll) super.update(payrollId, input);
    }

    @Override
    public void delete(String payrollId) {
        payrollRepository.delete(payrollId);
    }

    private Payroll savePayroll(Payroll payroll, boolean isOverride) {
        Optional<Payroll> payrollOptional = payrollRepository.findByUserNameAndMonth(payroll.getUserName(), payroll.getMonth());
        if(isOverride){
            payrollOptional.ifPresent(payroll1 -> payrollRepository.delete(payroll1));
            return payrollRepository.save(payroll);
        } else {
            if (payrollOptional.isPresent()) {
                return payrollOptional.get();
            } else {
                return payrollRepository.save(payroll);
            }
        }
    }

    private Payroll generatePayroll(User user, String month) {
        Payroll payroll = new Payroll();

        payroll.setBaseSalary(user.getSalary());
        SPF spf = new SPF(user.getSalary(), user.getSpfType());
        payroll.setUserName(user.getUserName());
        payroll.setMonth(month);
        payroll.setComments("Only for testing");
        payroll.setSpf(spf);
        payroll.setCompany(user.getCompany());
        double tax = TaxCalculator.doCalculate(user.getSalary(), spf.getPersonalSPF());
        payroll.setTax(tax);
        payroll.setWorkingDays(22);
        payroll.setFinalSalary(SalaryCalculator.calculateFinalSalary(user.getSalary(), 22, 400, tax, spf, 0));
        return payroll;
    }
}
