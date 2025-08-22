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

//    @Query(value = "SELECT new com.hms.profile_service.DTO.DoctorDetailsDTO(" +
//            "u.id, u.username, u.phone_number, d.name, " +
//            "p.qualification, p.years_of_experience, " +
//            "p.specialization, p.medical_registration_number, " +
//            "p.created_at, p.is_active) " +
//            "FROM users u " +
//            "JOIN department d ON u.department_id = d.id " +
//            "JOIN professional_information p ON u.professional_id = p.id",)
////            nativeQuery = true)
//    List<DoctorDetailsDTO> getAllDoctorsDetails();



@Query("SELECT new com.hms.profile_service.DTO.DoctorDetailsDTO(" +
        "u.id, u.username, u.phoneNumber, d.name, " +
        "p.qualification, p.yearsOfExperience, " +
        "p.specialization, p.medicalRegistrationNumber, " +
        "p.createdAt, p.isActive) " +
        "FROM User u " +
        "JOIN u.department d " +
        "JOIN u.professionalInformation p")
List<DoctorDetailsDTO> getAllDoctorsDetails();


}
