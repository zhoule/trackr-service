package com.xplusz.trackr.services;

import com.xplusz.trackr.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

    Expense findByPrimaryKey(String id);

    Page<Expense> getExpensesByUser(String userId, Pageable pageable);

    Page<Expense> getExpensesByPage(Pageable pageable);

    Expense save(Expense input);

    Expense update(final String expenseId, Expense input);

    void delete(final String expenseId);
}
