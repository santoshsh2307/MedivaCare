package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;
import com.hms.profile_service.entity.ProfessionalInformation;
import com.hms.profile_service.entity.User;
import com.hms.profile_service.exception.BadRequestException;
import com.hms.profile_service.exception.ConflictException;
import com.hms.profile_service.exception.InternalServerException;
import com.hms.profile_service.exception.ResourceNotFoundException;
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
        try {
            // Validate Username
            if (userOnBoardingDTO.getUsername() == null || userOnBoardingDTO.getUsername().isEmpty()) {
                throw new BadRequestException("Username cannot be null or empty");
            }

            // Check if username already exists
            if (userRepo.existsByUsername(userOnBoardingDTO.getUsername())) {
                throw new ConflictException("Username already exists. Please choose a different one.");
            }

            // Validate password
            if (userOnBoardingDTO.getPassword() == null || userOnBoardingDTO.getPassword().length() < 8) {
                throw new BadRequestException("Invalid password. Password must be at least 8 characters long.");
            }

            // Validate phone number
            if (userOnBoardingDTO.getPhoneNumber() == null || !userOnBoardingDTO.getPhoneNumber().matches("\\d{10}")) {
                throw new BadRequestException("Phone number must be 10 digits.");
            }

            // Check if phone number already exists
            if (userRepo.existsByPhoneNumber(userOnBoardingDTO.getPhoneNumber())) {
                throw new ConflictException("Phone number already exists: " + userOnBoardingDTO.getPhoneNumber());
            }

            // Check if Medical Registration Number is unique
            if (professionalInfoRepo.existsByMedicalRegistrationNumber(userOnBoardingDTO.getMedicalRegistrationNumber())) {
                ProfessionalInformation existing = professionalInfoRepo.findByMedicalRegistrationNumber(
                        userOnBoardingDTO.getMedicalRegistrationNumber());
                if (existing != null && !existing.getId().equals(userOnBoardingDTO.getProfessionalInfoId())) {
                    throw new ConflictException("Medical Registration Number already exists: " +
                            userOnBoardingDTO.getMedicalRegistrationNumber());
                }
            }

            // Map DTO -> Professional Info
            ProfessionalInformation professionalInformation = new ProfessionalInformation();
            professionalInformation.setMedicalRegistrationNumber(userOnBoardingDTO.getMedicalRegistrationNumber());
            professionalInformation.setDepartmentId(userOnBoardingDTO.getDepartmentId());
            professionalInformation.setQualification(userOnBoardingDTO.getQualification());
            professionalInformation.setYearsOfExperience(userOnBoardingDTO.getYearsOfExperience());
            professionalInformation.setCreatedAt(userOnBoardingDTO.getProfessionalCreatedAt());
            professionalInformation.setActive(userOnBoardingDTO.isActive());
            professionalInformation.setSpecialization(userOnBoardingDTO.getSpecialization());

            // Save Professional Info
            professionalInformation = professionalInfoRepo.save(professionalInformation);

            // Map DTO -> User
            User user = new User();
            user.setUsername(userOnBoardingDTO.getUsername());
            user.setEnabled(userOnBoardingDTO.isEnabled());
            user.setPassword(userOnBoardingDTO.getPassword()); // ideally encrypt here
            user.setDepartmentId(userOnBoardingDTO.getDepartmentId());
            user.setCreatedAt(userOnBoardingDTO.getCreatedAt());
            user.setPhoto(userOnBoardingDTO.getPhoto());
            user.setPhoneNumber(userOnBoardingDTO.getPhoneNumber());
            user.setEmergencyContactNumber(userOnBoardingDTO.getEmergencyContactNumber());
            user.setLastLoginAt(userOnBoardingDTO.getLastLoginAt());
            user.setUpdatedAt(userOnBoardingDTO.getUpdatedAt());
            user.setProfessionalInfoId(professionalInformation.getId());

            // Save User
            userRepo.save(user);

            return userOnBoardingDTO;

        } catch (BadRequestException | ConflictException e) {
            throw e; // rethrow to controller advice
        } catch (Exception e) {
            throw new InternalServerException("Failed to save user profile: " + e.getMessage());
        }
    }

    @Override
    public List<DoctorDropDownDTO> getAllDoctorsDropDown() {
        try {
            List<DoctorDropDownDTO> doctors = userRepo.findAllDoctorsDropDown();

            if (doctors == null || doctors.isEmpty()) {
                throw new ResourceNotFoundException("No doctors found in the system");
            }

            boolean missingDept = doctors.stream().anyMatch(d -> d.getName() == null || d.getName().isEmpty());
            if (missingDept) {
                throw new ResourceNotFoundException("Some doctors are missing department name");
            }

            return doctors;

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalServerException("Failed to fetch doctors: " + ex.getMessage());
        }
    }

    @Override
    public List<DoctorDetailsDTO> getAllDoctorsDetails() {
        List<DoctorDetailsDTO> doctors = userRepo.getAllDoctorsDetails();
        if (doctors == null || doctors.isEmpty()) {
            throw new ResourceNotFoundException("No doctors found");
        }
        return doctors;
    }
}
