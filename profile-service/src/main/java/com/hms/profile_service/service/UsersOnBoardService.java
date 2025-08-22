package com.hms.profile_service.service;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;

import java.util.List;

public interface UsersOnBoardService {
    UserOnBoardingDTO saveUserProfile(UserOnBoardingDTO userOnBoardingDTO);

    List<DoctorDropDownDTO> getAllDoctorsDropDown();

    List<DoctorDetailsDTO> getAllDoctorsDetails();

}
