package com.xplusz.trackr.controllers;

import com.xplusz.trackr.model.*;
import com.xplusz.trackr.resource.assemblers.*;
import com.xplusz.trackr.services.HolidayService;
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

import java.security.acl.Owner;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private static final String OWNER = "authentication.name == #userName";

    private static final String ADMIN = "hasRole('ADMIN')";

    private final FestivalResourceAssembler festivalResourceAssembler;

    private final HolidayService holidayService;

    private final HolidayTypeResourceAssembler holidayTypeResourceAssembler;

    private final WorkdaysResourceAssembler workdaysResourceAssembler;

    private final HolidayBalancesResourceAssembler holidayBalancesResourceAssembler;

    private final HolidayPolicyResourceAssembler holidayPolicyResourceAssembler;

    private final HolidayRecordResourceAssembler holidayRecordResourceAssembler;


    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public HolidayController(
            HolidayService holidayService,
            FestivalResourceAssembler festivalResourceAssembler,
            HolidayTypeResourceAssembler holidayTypeResourceAssembler,
            WorkdaysResourceAssembler workdaysResourceAssembler,
            HolidayBalancesResourceAssembler holidayBalancesResourceAssembler,
            HolidayPolicyResourceAssembler holidayPolicyResourceAssembler,
            HolidayRecordResourceAssembler holidayRecordResourceAssembler
    ) {
        this.holidayService = holidayService;
        this.festivalResourceAssembler = festivalResourceAssembler;
        this.holidayTypeResourceAssembler = holidayTypeResourceAssembler;
        this.workdaysResourceAssembler = workdaysResourceAssembler;
        this.holidayBalancesResourceAssembler = holidayBalancesResourceAssembler;
        this.holidayPolicyResourceAssembler = holidayPolicyResourceAssembler;
        this.holidayRecordResourceAssembler = holidayRecordResourceAssembler;
    }

    @RequestMapping(path = "/init/company/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Init the holiday with company.")
    public ResponseEntity<?> initHoliday(@PathVariable String company) {
        holidayService.initFestivals(company);
        holidayService.initWorkdays(company);
        return new ResponseEntity<>("Success to init the holiday", HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/festival/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the festival by festival id.")
    public ResponseEntity<?> getFestivalById(@PathVariable String id) {
        Festival festival = holidayService.findFestivalByPrimaryKey(id);
        return new ResponseEntity<>(this.festivalResourceAssembler.toResource(festival), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/festival", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new Festival")
    public ResponseEntity<?> createFestival(@Validated @RequestBody Festival input) {
        Festival festival = holidayService.saveFestival(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, festival.getPrimaryKey()).getFestivalById(festival.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/festival/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the festival by company name.")
    public ResponseEntity<?> getFestivalByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                               PagedResourcesAssembler<Festival> assembler) {
        Page<Festival> festivals = holidayService.getFestivalByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(festivals, this.festivalResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/festival/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update festival by given festival id")
    public ResponseEntity<?> updateFestival(@PathVariable String id,
                                        @Validated @RequestBody Festival input) {
        Festival festival = holidayService.updateFestival(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, festival.getPrimaryKey()).getFestivalById(festival.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }


//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/festival/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete festival by given festival id")
    public ResponseEntity<Void> deleteFestival(@PathVariable String id) {
        this.holidayService.deleteFestival(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/workdays/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the workdays by workdays id.")
    public ResponseEntity<?> getWorkdaysById(@PathVariable String id) {
        Workdays workdays = holidayService.findWorkdaysByPrimaryKey(id);
        return new ResponseEntity<>(this.workdaysResourceAssembler.toResource(workdays), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/workdays/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update workdays by given workdays id")
    public ResponseEntity<?> updateWorkdays(@PathVariable String id,
                                            @Validated @RequestBody Workdays input) {
        Workdays workdays = holidayService.updateWorkdays(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, workdays.getPrimaryKey()).getWorkdaysById(workdays.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }

    //    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/workdays/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the workdays by company name.")
    public ResponseEntity<?> getWorkdaysByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                     PagedResourcesAssembler<Workdays> assembler) {
        Page<Workdays> workdays = holidayService.getWorkdaysByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(workdays, this.workdaysResourceAssembler), HttpStatus.OK);
    }


//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayType/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayType by holidayType id.")
    public ResponseEntity<?> getHolidayTypeById(@PathVariable String id) {
        HolidayType holidayType = holidayService.findHolidayTypeByPrimaryKey(id);
        return new ResponseEntity<>(this.holidayTypeResourceAssembler.toResource(holidayType), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayType", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new HolidayType")
    public ResponseEntity<?> createHolidayType(@Validated @RequestBody HolidayType input) {
        HolidayType holidayType = holidayService.saveHolidayType(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayType.getPrimaryKey()).getHolidayTypeById(holidayType.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayType/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayType by company name.")
    public ResponseEntity<?> getHolidayTypeByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                  PagedResourcesAssembler<HolidayType> assembler) {
        Page<HolidayType> holidayTypes = holidayService.getHolidayTypeByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(holidayTypes, this.holidayTypeResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayType/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update holidayType by given festival id")
    public ResponseEntity<?> updateHolidayType(@PathVariable String id,
                                            @Validated @RequestBody HolidayType input) {
        HolidayType holidayType = holidayService.updateHolidayType(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayType.getPrimaryKey()).getHolidayTypeById(holidayType.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }


//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayType/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete holidayType by given holidayType id")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable String id) {
        this.holidayService.deleteHolidayType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayBalances/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayBalances by holidayType id.")
    public ResponseEntity<?> getHolidayBalancesById(@PathVariable String id) {
        HolidayBalances holidayBalances = holidayService.findHolidayBalancesByPrimaryKey(id);
        return new ResponseEntity<>(this.holidayBalancesResourceAssembler.toResource(holidayBalances), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayBalances", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new HolidayType")
    public ResponseEntity<?> createHolidayBalances(@Validated @RequestBody HolidayBalances input) {
        HolidayBalances holidayBalances = holidayService.saveHolidayBalances(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayBalances.getPrimaryKey()).getHolidayBalancesById(holidayBalances.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayBalances/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayBalances by company name.")
    public ResponseEntity<?> getHolidayBalancesByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                     PagedResourcesAssembler<HolidayBalances> assembler) {
        Page<HolidayBalances> holidayBalances = holidayService.getHolidayBalancesByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(holidayBalances, this.holidayBalancesResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayBalances/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update holidayBalance by given festival id")
    public ResponseEntity<?> updateHolidayBalances(@PathVariable String id,
                                               @Validated @RequestBody HolidayBalances input) {
        HolidayBalances holidayBalances = holidayService.updateHolidayBalances(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayBalances.getPrimaryKey()).getHolidayBalancesById(holidayBalances.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }


//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayBalance/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete holidayBalance by given holidayType id")
    public ResponseEntity<Void> deleteHolidayBalance(@PathVariable String id) {
        this.holidayService.deleteHolidayBalances(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holiholidayPolicydayBalances/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayPolicy by holidayPolicy id.")
    public ResponseEntity<?> getHolidayPolicyById(@PathVariable String id) {
        HolidayPolicy holidayPolicy = holidayService.findHolidayPolicyByPrimaryKey(id);
        return new ResponseEntity<>(this.holidayPolicyResourceAssembler.toResource(holidayPolicy), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/HolidayPolicy", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new HolidayPolicy")
    public ResponseEntity<?> createHolidayPolicy(@Validated @RequestBody HolidayPolicy input) {
        HolidayPolicy holidayPolicy = holidayService.saveHolidayPolicy(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayPolicy.getPrimaryKey()).getHolidayPolicyById(holidayPolicy.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayPolicy/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayPolicy by company name.")
    public ResponseEntity<?> getHolidayPolicyByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                         PagedResourcesAssembler<HolidayPolicy> assembler) {
        Page<HolidayPolicy> holidayPolicies = holidayService.getHolidayPolicyByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(holidayPolicies, this.holidayPolicyResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayPolicy/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update holidayPolicy by given festival id")
    public ResponseEntity<?> updateHolidayPolicy(@PathVariable String id,
                                               @Validated @RequestBody HolidayPolicy input) {
        HolidayPolicy holidayPolicy = holidayService.updateHolidayPolicy(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayPolicy.getPrimaryKey()).getHolidayPolicyById(holidayPolicy.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }


//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayPolicy/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete holidayPolicy by given holidayType id")
    public ResponseEntity<Void> deleteHolidayPolicy(@PathVariable String id) {
        this.holidayService.deleteHolidayPolicy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayRecord/id/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the holidayRecord by holidayType id.")
    public ResponseEntity<?> getHolidayRecordById(@PathVariable String id) {
        HolidayRecord holidayRecord = holidayService.findHolidayRecordByPrimaryKey(id);
        return new ResponseEntity<>(this.holidayRecordResourceAssembler.toResource(holidayRecord), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new HolidayRecord")
    public ResponseEntity<?> createHolidayRecord(@Validated @RequestBody HolidayRecord input) {
        HolidayRecord holidayRecord = holidayService.saveHolidayRecord(input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayRecord.getPrimaryKey()).getHolidayRecordById(holidayRecord.getPrimaryKey())).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

//    @PreAuthorize(ADMIN + " or " + OWNER)
    @RequestMapping(path = "/holidayRecord/{company}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the HolidayRecord by company name.")
    public ResponseEntity<?> getHolidayRecordsByCompany(@PathVariable String company, @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                         PagedResourcesAssembler<HolidayRecord> assembler) {
        Page<HolidayRecord> holidayRecords = holidayService.getHolidayRecordByCompany(company, pageable);

        return new ResponseEntity<>(assembler.toResource(holidayRecords, this.holidayRecordResourceAssembler), HttpStatus.OK);
    }

//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayRecord/id/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update HolidayRecord by given holidayRecord id")
    public ResponseEntity<?> updateHolidayRecord(@PathVariable String id,
                                               @Validated @RequestBody HolidayRecord input) {
        HolidayRecord holidayRecord = holidayService.updateHolidayRecord(id, input);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(HolidayController.class, holidayRecord.getPrimaryKey()).getHolidayRecordById(holidayRecord.getPrimaryKey())).toUri());

        return new ResponseEntity<>("The resource was updated successful.", httpHeaders, HttpStatus.NO_CONTENT);
    }


//    @PreAuthorize(ADMIN)
    @RequestMapping(path = "/holidayRecord/id/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete HolidayRecord by given HolidayRecord id")
    public ResponseEntity<Void> deleteHolidayRecord(@PathVariable String id) {
        this.holidayService.deleteHolidayRecord(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
