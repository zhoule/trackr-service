package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.Claim;
import com.xplusz.trackr.resource.assemblers.ClaimResourceAssembler;
import com.xplusz.trackr.services.ClaimService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private final ClaimResourceAssembler claimResourceAssembler;

    private final ClaimService claimService;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public ClaimController(
            ClaimService claimService,
            ClaimResourceAssembler claimResourceAssembler
    ) {
        this.claimService = claimService;
        this.claimResourceAssembler = claimResourceAssembler;
    }

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the claim by claim id.")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Claim claim = claimService.findByPrimaryKey(id);
        return new ResponseEntity<>(this.claimResourceAssembler.toResource(claim), HttpStatus.OK);
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/company/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the claim by company name.")
    public ResponseEntity<?> getClaimByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                               PagedResourcesAssembler<Claim> assembler) {
        Page<Claim> claims = claimService.getClaimByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(claims, this.claimResourceAssembler), HttpStatus.OK);
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the claim by user id.")
    public ResponseEntity<?> getClaimByUser(@PathVariable String userId, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                               PagedResourcesAssembler<Claim> assembler) {
        Page<Claim> claims = claimService.getClaimByCompany(userId, pageable);

        return new ResponseEntity<>(assembler.toResource(claims, this.claimResourceAssembler), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new claim")
    public ResponseEntity<?> create(@Validated @RequestBody Claim input) {
        Claim claim = claimService.save(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(ClaimController.class, claim.getPrimaryKey()).getById(claim.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update claim by given claim id")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @Validated @RequestBody Claim input) {
        Claim claim = claimService.update(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(ClaimController.class, claim.getPrimaryKey()).getById(claim.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete claim by given claim id")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        this.claimService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
