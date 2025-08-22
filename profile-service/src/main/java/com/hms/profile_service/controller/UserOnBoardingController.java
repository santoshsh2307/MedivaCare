package com.hms.profile_service.controller;

import com.hms.profile_service.DTO.DoctorDetailsDTO;
import com.hms.profile_service.DTO.DoctorDropDownDTO;
import com.hms.profile_service.DTO.UserOnBoardingDTO;
import com.hms.profile_service.entity.Department;
import com.hms.profile_service.entity.User;
import com.hms.profile_service.service.UsersOnBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/profile/onboardingController")
@RestController
public class UserOnBoardingController {

@Autowired
private UsersOnBoardService usersOnBoardService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/onBoardDoctor")
    public ResponseEntity<UserOnBoardingDTO>saveUserProfile(@RequestBody UserOnBoardingDTO userOnBoardingDTO){
        UserOnBoardingDTO  saveUser=  usersOnBoardService.saveUserProfile(userOnBoardingDTO);
        return ResponseEntity.ok(saveUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/getAllDoctorsDropDown")
    public ResponseEntity<List<DoctorDropDownDTO>> getAllDoctorsDropDown() {
        return ResponseEntity.ok(usersOnBoardService.getAllDoctorsDropDown());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/getAllDoctorsDetails")
    public ResponseEntity<List<DoctorDetailsDTO>> getAllDoctorsDetails() {
        return ResponseEntity.ok(usersOnBoardService.getAllDoctorsDetails());
    }
}
