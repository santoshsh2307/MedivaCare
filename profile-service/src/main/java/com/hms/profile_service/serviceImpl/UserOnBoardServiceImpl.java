package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;
import com.hms.profile_service.entity.Department;
import com.hms.profile_service.entity.ProfessionalInformation;
import com.hms.profile_service.entity.User;
import com.hms.profile_service.repository.DepartmentRepo;
import com.hms.profile_service.repository.ProfessionalInfoRepo;
import com.hms.profile_service.repository.RoleRepository;
import com.hms.profile_service.repository.UserRepo;
import com.hms.profile_service.service.UsersOnBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOnBoardServiceImpl implements UsersOnBoardService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionalInfoRepo professionalInfoRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public UserOnBoardingDTO saveUserProfile(UserOnBoardingDTO userOnBoardingDTO) {
        // ========== Map DTO -> User ==========
        User user = new User();
        user.setUsername(userOnBoardingDTO.getUsername());
        user.setEnabled(userOnBoardingDTO.isEnabled());
        user.setPassword(userOnBoardingDTO.getPassword());
        user.setDepartmentId(userOnBoardingDTO.getDepartmentId());
        user.setCreatedAt(userOnBoardingDTO.getCreatedAt());
        user.setPhoto(userOnBoardingDTO.getPhoto());
        user.setPhoneNumber(userOnBoardingDTO.getPhoneNumber());
        user.setEmergencyContactNumber(userOnBoardingDTO.getEmergencyContactNumber());
        user.setLastLoginAt(userOnBoardingDTO.getLastLoginAt());
        user.setUpdatedAt(userOnBoardingDTO.getUpdatedAt());

//        // Map Set<String> roles -> Set<Role>
//        Set<Role> roles = userOnBoardingDTO.getRoles().stream()
//                .map(roleName -> roleRepository.findByName(roleName)
//                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
//                .collect(Collectors.toSet());
//        user.setRoles(roles);

        // Save Professional Information first
        ProfessionalInformation professionalInformation = new ProfessionalInformation();
        professionalInformation.setMedicalRegistrationNumber(userOnBoardingDTO.getMedicalRegistrationNumber());
        professionalInformation.setDepartmentId(userOnBoardingDTO.getDepartmentId());
        professionalInformation.setQualification(userOnBoardingDTO.getQualification());
        professionalInformation.setYearsOfExperience(userOnBoardingDTO.getYearsOfExperience());
        professionalInformation.setCreatedAt(userOnBoardingDTO.getProfessionalCreatedAt());
        professionalInformation.setActive(userOnBoardingDTO.isActive());
        professionalInformation.setSpecialization(userOnBoardingDTO.getSpecialization());

        professionalInformation = professionalInfoRepo.save(professionalInformation);

        // link ProfessionalInformation -> User
        user.setProfessionalInfoId(professionalInformation.getId());

        // Save User
        userRepo.save(user);

//        Department department = new Department();
//        department.setName(userOnBoardingDTO.getName());

        return userOnBoardingDTO;
    }

    @Override
    public List<DoctorDropDownDTO> getAllDoctorsDropDown() {
        return userRepo.findAllDoctorsDropDown();
    }

    @Override
    public List<DoctorDetailsDTO> getAllDoctorsDetails() {
        return userRepo.getAllDoctorsDetails();

    }

}
