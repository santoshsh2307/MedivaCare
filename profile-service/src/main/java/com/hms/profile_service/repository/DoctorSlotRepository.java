package com.hms.profile_service.repository;

import com.hms.profile_service.model.DoctorSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {

    List<DoctorSlot> findByDoctorIdAndDateBetween(Long doctorId, LocalDate startDate, LocalDate endDate);

    List<DoctorSlot> findByDoctorIdAndDate(Long doctorId, LocalDate date);

    List<DoctorSlot> findByDoctorIdAndStatus(Long doctorId, String status);

    DoctorSlot findByDoctorIdAndDateAndTime(Long doctorId, LocalDate date, LocalTime time);
}

