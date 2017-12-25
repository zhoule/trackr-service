package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.BaseEntity;
import com.xplusz.trackr.model.Expense;
import com.xplusz.trackr.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.xplusz.trackr.utils.Constants.EXPENSE_STATUS_UNCLAIMED;

@Service
public class ExpenseServiceImpl extends BaseService implements com.xplusz.trackr.services.ExpenseService{

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, MongoOperations mongoOperations) {
        super(mongoOperations);
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense findByPrimaryKey(String id) {
        Optional<Expense> expense = expenseRepository.findByPrimaryKey(id);
        return expense.orElseThrow(() -> new EntityNotFoundException("Expense"));
    }

    @Override
    public Page<Expense> getExpensesByUser(String userId, Pageable pageable) {
       return expenseRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Expense> getExpensesByPage(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public Expense save(Expense input) {
        if(StringUtils.isEmpty(input.getClaimId())){
            input.setStatus(EXPENSE_STATUS_UNCLAIMED);
        }
        //Todo if the claim id is not empty and the claim status is open we should set the expense status to EXPENSE_STATUS_UNSUBMITTED
        return expenseRepository.save(input);
    }

    @Override
    public Expense update(String expenseId, Expense input) {
        return (Expense) super.update(expenseId, input);
    }

    @Override
    public void delete(String expenseId) {
        expenseRepository.delete(expenseId);
    }
}
