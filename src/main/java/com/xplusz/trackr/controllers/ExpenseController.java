package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.Expense;
import com.xplusz.trackr.resource.assemblers.ExpenseResourceAssembler;
import com.xplusz.trackr.services.ExpenseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private final ExpenseResourceAssembler expenseResourceAssembler;

    private final ExpenseService expenseService;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public ExpenseController(
            ExpenseService expenseService,
            ExpenseResourceAssembler expenseResourceAssembler
    ) {
        this.expenseService = expenseService;
        this.expenseResourceAssembler = expenseResourceAssembler;
    }

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the expense by payroll id.")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Expense expenses = expenseService.findByPrimaryKey(id);
        return new ResponseEntity<>(this.expenseResourceAssembler.toResource(expenses), HttpStatus.OK);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the expense by user id.")
    public ResponseEntity<?> getExpenseByUserId(
            @PathVariable String id,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            PagedResourcesAssembler<Expense> assembler) {
        Page<Expense> expenses = expenseService.getExpensesByUser(id, pageable);
        return new ResponseEntity<>(assembler.toResource(expenses, this.expenseResourceAssembler), HttpStatus.OK);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new expense")
    public ResponseEntity<?> create(@Validated @RequestBody Expense newExpense) {
        Expense expense = expenseService.save(newExpense);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(ExpenseController.class, expense.getPrimaryKey()).getById(expense.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update the exist expense by payroll id")
    public ResponseEntity<?> update(@PathVariable String id, @Validated @RequestBody Expense newExpense) {
        Expense expense = expenseService.update(id, newExpense);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(ExpenseController.class, expense.getPrimaryKey()).getById(expense.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete the expense by payroll id")
    public ResponseEntity<?> delete(@PathVariable String id) {
        expenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
