package com.hms.profile_service.serviceImpl;


import com.hms.profile_service.dto.DoctorAvailabilityRequest;
import com.hms.profile_service.model.DoctorAvailability;
import com.hms.profile_service.model.DoctorSlot;
import com.hms.profile_service.model.User;
import com.hms.profile_service.repository.DoctorAvailabilityRepository;
import com.hms.profile_service.repository.DoctorSlotRepository;
import com.hms.profile_service.repository.UserRepository;
import com.hms.profile_service.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorSlotRepository slotRepo;
    private final UserRepository userRepository;

    public DoctorAvailability saveAvailability(DoctorAvailabilityRequest request) {
        // 1. Save the main availability rule
        DoctorAvailability availability = DoctorAvailability.builder()
                .doctorId(request.getDoctorId())
                .startDate(request.getFromDate())
                .endDate(request.getToDate())
                .startTime(request.getDefaultStartTime())
                .endTime(request.getDefaultEndTime())
                .intervalMinutes(request.getIntervalMinutes())
                .weekdays(String.join(",", request.getWeekdays())) // store as comma-separated string
                .build();

        // 2. Save the availability
        DoctorAvailability saved = availabilityRepo.save(availability);

        // 3. Generate slots for 15 days from startDate
        LocalDate defaultEndDate = request.getFromDate().plusDays(15);
        generateSlots(saved, request.getFromDate(), defaultEndDate);

        // 4. Optional: handle custom weekday time ranges if provided
        if (request.getWeekdayTimeRanges() != null && !request.getWeekdayTimeRanges().isEmpty()) {
            Map<String, DoctorAvailability.TimeRange> modelWeekdayRanges = new HashMap<>();
            if (request.getWeekdayTimeRanges() != null) {
                request.getWeekdayTimeRanges().forEach((day, timeRange) -> {
                    modelWeekdayRanges.put(day, DoctorAvailability.TimeRange.builder()
                            .startTime(timeRange.getStartTime())
                            .endTime(timeRange.getEndTime())
                            .build());
                });
            }
            saved.setWeekdayTimeRanges(modelWeekdayRanges);
        }

        // 5. Optional: handle specific custom dates if provided
        if (request.getCustomDates() != null && !request.getCustomDates().isEmpty()) {
            List<DoctorAvailability.CustomDateAvailability> customDates = request.getCustomDates().stream()
                    .map(cd -> DoctorAvailability.CustomDateAvailability.builder()
                            .date(cd.getDate())
                            .startTime(cd.getStartTime())
                            .endTime(cd.getEndTime())
                            .availability(saved)
                            .build())
                    .collect(Collectors.toList());
            saved.setCustomDates(customDates);
        }

        // 6. Save again to persist nested structures
        return availabilityRepo.save(saved);
    }


    private void generateSlots(DoctorAvailability availability, LocalDate from, LocalDate to) {
        int interval = availability.getIntervalMinutes();

        // Convert weekdays string to uppercase for comparison
        String weekdays = availability.getWeekdays() != null ? availability.getWeekdays().toUpperCase() : "";

        for (LocalDate date = from; !date.isAfter(to) && !date.isAfter(availability.getEndDate()); date = date.plusDays(1)) {
            LocalTime startTime = availability.getStartTime();
            LocalTime endTime = availability.getEndTime();

            // 1. Check for specific date overrides
            if (availability.getCustomDates() != null) {
                for (DoctorAvailability.CustomDateAvailability cd : availability.getCustomDates()) {
                    if (cd.getDate().equals(date)) {
                        startTime = cd.getStartTime();
                        endTime = cd.getEndTime();
                        break;
                    }
                }
            }

            // 2. Check for per-weekday custom times
            if (availability.getWeekdayTimeRanges() != null) {
                String day = date.getDayOfWeek().toString().substring(0, 3); // Mon, Tue
                DoctorAvailability.TimeRange tr = availability.getWeekdayTimeRanges().get(day);
                if (tr != null) {
                    startTime = tr.getStartTime();
                    endTime = tr.getEndTime();
                }
            }

            // 3. Skip if day is not in default weekdays
            String day = date.getDayOfWeek().toString().substring(0, 3); // Mon, Tue, etc.
            if (startTime == null || endTime == null || !weekdays.contains(day)) {
                continue;
            }

            // Generate slots
            LocalTime slotTime = startTime;
            while (!slotTime.plusMinutes(interval).isAfter(endTime)) {
                DoctorSlot slot = DoctorSlot.builder()
                        .doctorId(availability.getDoctorId())
                        .date(date)
                        .time(slotTime)
                        .status("AVAILABLE")
                        .availability(availability)
                        .build();
                slotRepo.save(slot);

                slotTime = slotTime.plusMinutes(interval);
            }
        }
    }


    public List<DoctorAvailability> getAvailabilityByDoctor(Long doctorId) {
        return doctorAvailabilityRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<User> findActiveUsersByRole(String doctor) {
        return userRepository.findActiveUsersByRole("ROLE_DOCTOR");
    }


}


