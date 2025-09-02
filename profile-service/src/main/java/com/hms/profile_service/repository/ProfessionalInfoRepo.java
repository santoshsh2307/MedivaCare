package com.hms.profile_service.repository;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.entity.ProfessionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalInfoRepo extends JpaRepository<ProfessionalInformation,Long> {
    boolean existsByMedicalRegistrationNumber(String medicalRegistrationNumber);

    ProfessionalInformation findByMedicalRegistrationNumber(String medicalRegistrationNumber);


//    @Query("SELECT new com.hms.profile_service.DTO.DoctorDetailsDTO(" +
//            "u.id, u.username, u.phoneNumber, d.name, " +
//            "p.qualification, p.yearsOfExperience, " +
//            "p.qualification, p.medicalRegistrationNumber, " +
//            "p.createdAt, p.isActive) " +   // ✅ fix here
//            "FROM User u, ProfessionalInformation p, Department d " +
//            "WHERE u.professionalInfoId = p.id AND u.departmentId = d.id")
//    List<DoctorDetailsDTO> getAllDoctorsDetails();
}
