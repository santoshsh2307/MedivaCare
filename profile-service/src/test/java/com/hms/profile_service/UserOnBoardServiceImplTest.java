package com.hms.profile_service;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;
import com.hms.profile_service.entity.ProfessionalInformation;
import com.hms.profile_service.entity.User;
import com.hms.profile_service.exception.*;
import com.hms.profile_service.repository.*;
import com.hms.profile_service.serviceImpl.UserOnBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserOnBoardServiceImplTest {

    @Mock private UserRepo userRepo;
    @Mock private ProfessionalInfoRepo professionalInfoRepo;
    @Mock private RoleRepository roleRepository;
    @Mock private DepartmentRepo departmentRepo;

    @InjectMocks private UserOnBoardServiceImpl userOnBoardService;

    private UserOnBoardingDTO generateRandomUserDTO() {
        UserOnBoardingDTO dto = new UserOnBoardingDTO();
        dto.setUsername("user_" + UUID.randomUUID());
        dto.setPassword("Pass" + UUID.randomUUID().toString().substring(0, 8));
        dto.setPhoneNumber(String.format("%010d", ThreadLocalRandom.current().nextLong(0, 1_000_000_0000L)));
        dto.setMedicalRegistrationNumber("MED_" + UUID.randomUUID());
        dto.setDepartmentId(ThreadLocalRandom.current().nextLong(1, 10));
        dto.setQualification("MBBS_" + UUID.randomUUID().toString().substring(0,4));
        dto.setYearsOfExperience(ThreadLocalRandom.current().nextInt(1, 30));
        dto.setActive(true);
        return dto;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUserProfile_Positive() {
        UserOnBoardingDTO userDTO = generateRandomUserDTO();

        when(userRepo.existsByUsername(anyString())).thenReturn(false);
        when(userRepo.existsByPhoneNumber(anyString())).thenReturn(false);
        when(professionalInfoRepo.existsByMedicalRegistrationNumber(anyString())).thenReturn(false);

        ProfessionalInformation savedProfessionalInfo = new ProfessionalInformation();
        savedProfessionalInfo.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        when(professionalInfoRepo.save(any(ProfessionalInformation.class))).thenReturn(savedProfessionalInfo);
        when(userRepo.save(any(User.class))).thenReturn(new User());

        UserOnBoardingDTO result = userOnBoardService.saveUserProfile(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getUsername(), result.getUsername());
        verify(userRepo, times(1)).save(any(User.class));
        verify(professionalInfoRepo, times(1)).save(any(ProfessionalInformation.class));
    }

    @Test
    void saveUserProfile_UsernameExists_ThrowsConflictException() {
        UserOnBoardingDTO userDTO = generateRandomUserDTO();
        when(userRepo.existsByUsername(userDTO.getUsername())).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> userOnBoardService.saveUserProfile(userDTO));

        assertTrue(ex.getMessage().contains("Username already exists"));
    }

    @Test
    void getAllDoctorsDropDown_Positive() {
        DoctorDropDownDTO doctor = new DoctorDropDownDTO(ThreadLocalRandom.current().nextLong(1,100),
                "doctor_" + UUID.randomUUID(), "Dept_" + UUID.randomUUID());
        when(userRepo.findAllDoctorsDropDown()).thenReturn(Collections.singletonList(doctor));

        List<DoctorDropDownDTO> result = userOnBoardService.getAllDoctorsDropDown();

        assertNotNull(result);
        assertEquals(1, result.size());
                
        assertNotNull(result.get(0).getName());
    }

    @Test
    void getAllDoctorsDetails_Positive() {
        DoctorDetailsDTO doctor = new DoctorDetailsDTO(
                ThreadLocalRandom.current().nextLong(1,100),
                "doctor_" + UUID.randomUUID(),
                String.format("%010d", ThreadLocalRandom.current().nextLong(0, 1_000_000_0000L)),
                "Dept_" + UUID.randomUUID(),
                "MBBS",
                ThreadLocalRandom.current().nextInt(1,30),
                "Spec_" + UUID.randomUUID(),
                "MED_" + UUID.randomUUID(),
                java.time.LocalDate.now(),
                true
        );

        when(userRepo.getAllDoctorsDetails()).thenReturn(Collections.singletonList(doctor));

        List<DoctorDetailsDTO> result = userOnBoardService.getAllDoctorsDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getName());
    }
}
