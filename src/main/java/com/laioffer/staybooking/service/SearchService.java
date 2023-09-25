package com.laioffer.staybooking.service;

import com.laioffer.staybooking.repository.LocationRepository;
import com.laioffer.staybooking.repository.StayReservationDateRepository;
import com.laioffer.staybooking.repository.StayRepository;
import org.springframework.stereotype.Service;
import com.laioffer.staybooking.model.Stay;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
public class SearchService {
    private final StayRepository stayRepository;
    private final StayReservationDateRepository stayReservationDateRepository;
    private final LocationRepository locationRepository;

    public SearchService(StayRepository stayRepository, StayReservationDateRepository stayReservationDateRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayReservationDateRepository = stayReservationDateRepository;
        this.locationRepository = locationRepository;
    }

    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {

        // 1. search Elasticsearch
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
        if (stayIds == null || stayIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. search stays that already reserved
        Set<Long> reservedStayIds = stayReservationDateRepository.findByIdInAndDateBetween(stayIds, checkinDate, checkoutDate.minusDays(1));
        //checkout时间减去一天 因为当天别人可以checkin

        // 剔除已经被reserved
        List<Long> filteredStayIds = new ArrayList<>();
        for (Long stayId : stayIds) {
            if (!reservedStayIds.contains(stayId)) {
                filteredStayIds.add(stayId);
            }
        }
        // 3. check stay guest number >= guest number passed by search request
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }
}
