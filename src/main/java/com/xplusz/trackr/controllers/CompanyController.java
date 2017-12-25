package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.Company;
import com.xplusz.trackr.resource.assemblers.CompanyResourceAssembler;
import com.xplusz.trackr.services.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by hank on 4/10/17.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private CompanyService companyService;

    private CompanyResourceAssembler companyResourceAssembler;

    @Autowired
    public CompanyController(
            CompanyService companyService,
            CompanyResourceAssembler companyResourceAssembler
    ) {
        this.companyService = companyService;
        this.companyResourceAssembler = companyResourceAssembler;
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the company by company id.")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Company company = companyService.findByPrimaryKey(id);
        return new ResponseEntity<>(this.companyResourceAssembler.toResource(company), HttpStatus.OK);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new company")
    public ResponseEntity<?> create(@Validated @RequestBody Company newCompany) {
        Company company = companyService.save(newCompany);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(ExpenseController.class, company.getPrimaryKey()).getById(company.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update company by given company id")
    public ResponseEntity<?> updateCompany(@PathVariable String id,
                                        @Validated @RequestBody Company input) {
        Company company = companyService.update(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(CompanyController.class, company.getPrimaryKey()).getById(company.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete company by given company id")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        this.companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
