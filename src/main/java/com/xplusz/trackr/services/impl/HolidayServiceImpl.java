package com.xplusz.trackr.services.impl;

import com.xplusz.trackr.exceptions.EntityNotFoundException;
import com.xplusz.trackr.model.*;
import com.xplusz.trackr.repository.*;
import com.xplusz.trackr.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HolidayServiceImpl extends BaseService implements HolidayService {

    private final FestivalRepository festivalRepository;

    private final HolidayBalancesRepository holidayBalancesRepository;

    private final HolidayPolicyRepository holidayPolicyRepository;

    private final HolidayRecordRepository holidayRecordRepository;

    private final HolidayTypeRepository holidayTypeRepository;

    private final WorkDaysRepository workDaysRepository;

    @Autowired
    public HolidayServiceImpl(FestivalRepository festivalRepository,
                              HolidayBalancesRepository holidayBalancesRepository,
                              HolidayPolicyRepository holidayPolicyRepository,
                              HolidayRecordRepository holidayRecordRepository,
                              HolidayTypeRepository holidayTypeRepository,
                              WorkDaysRepository workDaysRepository,
                              MongoOperations mongoOperations) {
        super(mongoOperations);
        this.festivalRepository = festivalRepository;
        this.holidayRecordRepository = holidayRecordRepository;
        this.holidayPolicyRepository = holidayPolicyRepository;
        this.holidayBalancesRepository = holidayBalancesRepository;
        this.holidayTypeRepository = holidayTypeRepository;
        this.workDaysRepository = workDaysRepository;
    }

    @Override
    public Festival findFestivalByPrimaryKey(String festivalId) {
        Optional<Festival> claim = festivalRepository.findByPrimaryKey(festivalId);
        return claim.orElseThrow(() -> new EntityNotFoundException("Festival"));
    }

    @Override
    public Festival saveFestival(Festival festival) {
        return festivalRepository.save(festival);
    }

    @Override
    public Page<Festival> getFestivalByCompany(String company, Pageable pageable) {
        return festivalRepository.findByCompany(company, pageable);
    }

    @Override
    public Festival updateFestival(String festivalId, Festival input) {
        return (Festival) super.update(festivalId, input);
    }

    @Override
    public void deleteFestival(String festivalId) {
        festivalRepository.delete(festivalId);
    }

    @Override
    public void initFestivals(String company) {
        festivalRepository.findByCommon(true).forEach((festival) -> {
            Festival newFestival = new Festival();
            newFestival.setCompany(company);
            newFestival.setFestivalName(festival.getFestivalName());
            newFestival.setDescription(festival.getDescription());
            newFestival.setStartDate(festival.getStartDate());
            newFestival.setEndDate(festival.getEndDate());
            newFestival.setYear(festival.getYear());
            newFestival.setChanges(festival.getChanges());
            newFestival.setCommon(false);
            festivalRepository.save(newFestival);
        });
    }

    @Override
    public void initWorkdays(String company) {
        Workdays workdays = new Workdays();
        workdays.setMonday(true);
        workdays.setTuesday(true);
        workdays.setWednesday(true);
        workdays.setThursday(true);
        workdays.setFriday(true);
        workdays.setSaturday(false);
        workdays.setSunday(false);
        workdays.setCompany(company);
        workDaysRepository.save(workdays);
    }

    @Override
    public Workdays findWorkdaysByPrimaryKey(String workdaysId) {
        Optional<Workdays> workdays = workDaysRepository.findByPrimaryKey(workdaysId);
        return workdays.orElseThrow(() -> new EntityNotFoundException("Workdays"));
    }

    @Override
    public Workdays updateWorkdays(String workdaysId, Workdays input) {
        return (Workdays) super.update(workdaysId, input);
    }

    @Override
    public Page<Workdays> getWorkdaysByCompany(String company, Pageable pageable) {
        return workDaysRepository.findByCompany(company, pageable);
    }

    @Override
    public HolidayType findHolidayTypeByPrimaryKey(String holidayTypeId) {
        Optional<HolidayType> holidayTypes = holidayTypeRepository.findByPrimaryKey(holidayTypeId);
        return holidayTypes.orElseThrow(() -> new EntityNotFoundException("HolidayType"));
    }

    @Override
    public HolidayType saveHolidayType(HolidayType holidayType) {
        return holidayTypeRepository.save(holidayType);
    }

    @Override
    public Page<HolidayType> getHolidayTypeByCompany(String company, Pageable pageable) {
        return holidayTypeRepository.findByCompany(company, pageable);
    }

    @Override
    public HolidayType updateHolidayType(String holidayTypeId, HolidayType holidayType) {
        return (HolidayType) super.update(holidayTypeId, holidayType);
    }

    @Override
    public void deleteHolidayType(String holidayTypeId) {
        festivalRepository.delete(holidayTypeId);
    }

    @Override
    public HolidayBalances findHolidayBalancesByPrimaryKey(String holidayBalancesId) {
        Optional<HolidayBalances> holidayBalances = holidayBalancesRepository.findByPrimaryKey(holidayBalancesId);
        return holidayBalances.orElseThrow(() -> new EntityNotFoundException("HolidayBalances"));
    }

    @Override
    public HolidayBalances saveHolidayBalances(HolidayBalances holidayBalances) {
        return holidayBalancesRepository.save(holidayBalances);
    }

    @Override
    public Page<HolidayBalances> getHolidayBalancesByCompany(String company, Pageable pageable) {
        return holidayBalancesRepository.findByCompany(company, pageable);
    }

    @Override
    public void deleteHolidayBalances(String HolidayBalancesId) {
        holidayBalancesRepository.delete(HolidayBalancesId);
    }

    @Override
    public HolidayBalances updateHolidayBalances(String holidayBalancesId, HolidayBalances input) {
        return (HolidayBalances)super.update(holidayBalancesId, input);
    }

    @Override
    public HolidayPolicy findHolidayPolicyByPrimaryKey(String holidayPolicyId) {
        Optional<HolidayPolicy> holidayPolicy = holidayPolicyRepository.findByPrimaryKey(holidayPolicyId);
        return holidayPolicy.orElseThrow(() -> new EntityNotFoundException("HolidayPolicy"));
    }

    @Override
    public HolidayPolicy saveHolidayPolicy(HolidayPolicy holidayPolicy) {
        return holidayPolicyRepository.save(holidayPolicy);
    }

    @Override
    public Page<HolidayPolicy> getHolidayPolicyByCompany(String company, Pageable pageable) {
        return holidayPolicyRepository.findByCompany(company, pageable);
    }

    @Override
    public void deleteHolidayPolicy(String holidayPolicyId) {
        holidayPolicyRepository.delete(holidayPolicyId);
    }

    @Override
    public HolidayPolicy updateHolidayPolicy(String holidayPolicyId, HolidayPolicy input) {
        return (HolidayPolicy)super.update(holidayPolicyId, input);
    }

    @Override
    public HolidayRecord findHolidayRecordByPrimaryKey(String holidayRecordId) {
        Optional<HolidayRecord> holidayRecord = holidayRecordRepository.findByPrimaryKey(holidayRecordId);
        return holidayRecord.orElseThrow(() -> new EntityNotFoundException("HolidayPolicy"));
    }

    @Override
    public HolidayRecord saveHolidayRecord(HolidayRecord holidayRecord) {
        return holidayRecordRepository.save(holidayRecord);
    }

    @Override
    public Page<HolidayRecord> getHolidayRecordByCompany(String company, Pageable pageable) {
        return holidayRecordRepository.findByCompany(company, pageable);
    }

    @Override
    public void deleteHolidayRecord(String holidayRecordId) {
        holidayRecordRepository.delete(holidayRecordId);
    }

    @Override
    public HolidayRecord updateHolidayRecord(String holidayRecordId, HolidayRecord input) {
        return (HolidayRecord)super.update(holidayRecordId, input);
    }

    @Override
    public Map getWorkStatusInMonth(String username, String company, String year, String month) {
        List workdays = new ArrayList<>();
        Map<String, List> daysByType = new HashMap<String, List>();
        holidayRecordRepository.findByCompanyAndUsernameAndYear(company, username, year).forEach(record -> {
            record.getDates().forEach(date -> {
                if(new Date(date).getMonth() == Integer.parseInt(month)) {
                    workdays.add(date);
                    if(daysByType.get(record.getType()) == null) {
                        daysByType.put(record.getType(), new ArrayList<>());
                    }
                    daysByType.get(record.getType()).add(date);
                }
            });
        });
        Map result = new HashMap<>();
        result.put("workdays", workdays);
        result.put("daysByType", daysByType);
        return result;
    }
}
