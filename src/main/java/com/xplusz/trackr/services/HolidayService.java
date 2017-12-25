package com.xplusz.trackr.services;

import com.xplusz.trackr.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface HolidayService {
    Festival findFestivalByPrimaryKey(final String festivalId);

    Festival saveFestival(Festival festival);

    Page<Festival> getFestivalByCompany(String company, Pageable pageable);

    Festival updateFestival(final String festivalId, Festival input);

    void deleteFestival(final String festivalId);

    void initFestivals(String company);

    void initWorkdays(String company);

    Workdays findWorkdaysByPrimaryKey(final String workdaysId);

    Workdays updateWorkdays(final String workdaysId, Workdays input);

    Page<Workdays> getWorkdaysByCompany(String company, Pageable pageable);

    HolidayType findHolidayTypeByPrimaryKey(final String holidayTypeId);

    HolidayType saveHolidayType(HolidayType holidayType);

    Page<HolidayType> getHolidayTypeByCompany(String company, Pageable pageable);

    HolidayType updateHolidayType(final String holidayTypeId, HolidayType holidayType);

    void deleteHolidayType(final String holidayTypeId);

    HolidayBalances findHolidayBalancesByPrimaryKey(final  String holidayBalancesId);

    HolidayBalances saveHolidayBalances(HolidayBalances holidayBalances);

    Page<HolidayBalances> getHolidayBalancesByCompany(String company, Pageable pageable);

    void deleteHolidayBalances(final String HolidayBalancesId);

    HolidayBalances updateHolidayBalances(final String HolidayBalancesId, HolidayBalances input);

    HolidayPolicy findHolidayPolicyByPrimaryKey(final  String holidayPolicyId);

    HolidayPolicy saveHolidayPolicy(HolidayPolicy holidayPolicy);

    Page<HolidayPolicy> getHolidayPolicyByCompany(String company, Pageable pageable);

    void deleteHolidayPolicy(final String holidayPolicyId);

    HolidayPolicy updateHolidayPolicy(final String holidayPolicyId, HolidayPolicy input);

    HolidayRecord findHolidayRecordByPrimaryKey(final  String holidayRecordId);

    HolidayRecord saveHolidayRecord(HolidayRecord holidayRecord);

    Page<HolidayRecord> getHolidayRecordByCompany(String company, Pageable pageable);

    void deleteHolidayRecord(final String holidayRecordId);

    HolidayRecord updateHolidayRecord(final String holidayRecordId, HolidayRecord input);

    Map getWorkStatusInMonth(String username, String company, String year, String month);
}
