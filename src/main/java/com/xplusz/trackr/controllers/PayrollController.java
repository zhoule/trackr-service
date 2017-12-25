package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.Payroll;
import com.xplusz.trackr.resource.assemblers.PayrollResourceAssembler;
import com.xplusz.trackr.services.PayrollService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private final PayrollResourceAssembler payrollResourceAssembler;

    private final PayrollService payrollService;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public PayrollController(
            PayrollService payrollService,
            PayrollResourceAssembler payrollResourceAssembler
    ) {
        this.payrollService = payrollService;
        this.payrollResourceAssembler = payrollResourceAssembler;
    }

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get payroll by given payroll id")
    public ResponseEntity<ResourceSupport> getById(@PathVariable String id) {
        Payroll payroll = payrollService.findByPrimaryKey(id);
        return new ResponseEntity<>(this.payrollResourceAssembler.toResource(payroll), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/user/{username}/month/{month}/generate", method = RequestMethod.GET)
    @ApiOperation(value = "Generate the payroll by the given username and month.")
    public ResponseEntity<?> generateByUser(@PathVariable String username, @PathVariable String month) {
        Payroll payroll = payrollService.generatePayrollByUser(username, month, true);
        return new ResponseEntity<>(this.payrollResourceAssembler.toResource(payroll), HttpStatus.OK);
    }

    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/company/{company}/month/{month}/generate", method = RequestMethod.GET)
    @ApiOperation(value = "Generate the payroll by the given company name and month.")
    public ResponseEntity<?> generateByCompany(@PathVariable String company,
                                               @PathVariable String month) {
        payrollService.generatePayrollByCompany(company, month, true);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/company/{company}/month/{month}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the payroll by the given company name and month.")
    public ResponseEntity<?> getPayrollByCompany(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            PagedResourcesAssembler<Payroll> assembler,
            @PathVariable String company,
            @PathVariable String month) {
        Page<Payroll> payrolls = payrollService.findByCompanyAndMonth(company, month, pageable);
        return new ResponseEntity<>(assembler.toResource(payrolls, this.payrollResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/user/{username}/month/{month}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the payroll by the given username and month.")
    public ResponseEntity<?> getPayrollByUser(@PathVariable String username, @PathVariable String month) {
        Payroll payroll = payrollService.findByUserNameAndMonth(username, month);
        return new ResponseEntity<>(this.payrollResourceAssembler.toResource(payroll), HttpStatus.OK);
    }
}

