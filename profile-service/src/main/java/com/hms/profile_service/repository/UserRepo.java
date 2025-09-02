package com.hms.profile_service.repository;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;
import com.hms.profile_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo  extends JpaRepository<User,Long> {

    @Query("SELECT new com.hms.profile_service.DTO.DoctorDropDownDTO(u.id, u.username, d.name) " +
            "FROM User u JOIN Department d ON u.departmentId = d.id")
    List<DoctorDropDownDTO> findAllDoctorsDropDown();


@Query("SELECT new com.hms.profile_service.DTO.DoctorDetailsDTO(" +
        "u.id, u.username, u.phoneNumber, d.name, " +
        "p.qualification, p.yearsOfExperience, " +
        "p.specialization, p.medicalRegistrationNumber, " +
        "p.createdAt, p.isActive) " +
        "FROM User u " +
        "JOIN u.department d " +
        "JOIN u.professionalInformation p")
List<DoctorDetailsDTO> getAllDoctorsDetails();


    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
}
