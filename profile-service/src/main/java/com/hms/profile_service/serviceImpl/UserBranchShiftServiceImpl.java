package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.model.UserBranchShift;
import com.hms.profile_service.repository.UserBranchShiftRepository;
import com.hms.profile_service.service.UserBranchShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.hms.profile_service.dto.UserBranchShiftRequest;
import com.hms.profile_service.model.User;
import com.hms.profile_service.model.UserBranchShift;
import com.hms.profile_service.repository.UserBranchShiftRepository;
import com.hms.profile_service.repository.UserRepository;
import com.hms.profile_service.service.UserBranchShiftService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBranchShiftServiceImpl implements UserBranchShiftService {

    private final UserBranchShiftRepository shiftRepository;
    private final UserRepository userRepository;

    public UserBranchShiftServiceImpl(UserBranchShiftRepository shiftRepository, UserRepository userRepository) {
        this.shiftRepository = shiftRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserBranchShift> getUserShifts(Long userId) {
        return shiftRepository.findByUserId(userId);
    }

    @Override
    public UserBranchShift submitShiftRequest(Long userId, UserBranchShiftRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");

        UserBranchShift shift = new UserBranchShift();
        shift.setUser(userOpt.get());
       // shift.setBranchId(request.getBranchId());
        //shift.setShiftDate(request.getShiftDate());
        shift.setStartTime(request.getStartTime());
        shift.setEndTime(request.getEndTime());
        shift.setStatus("PENDING");

        return shiftRepository.save(shift);
    }

    @Override
    public UserBranchShift approveShift(Long shiftId) {
        Optional<UserBranchShift> opt = shiftRepository.findById(shiftId);
        if (opt.isEmpty()) throw new RuntimeException("Shift not found");
        UserBranchShift shift = opt.get();
        shift.setStatus("APPROVED");
        return shiftRepository.save(shift);
    }

    @Override
    public UserBranchShift rejectShift(Long shiftId) {
        Optional<UserBranchShift> opt = shiftRepository.findById(shiftId);
        if (opt.isEmpty()) throw new RuntimeException("Shift not found");
        UserBranchShift shift = opt.get();
        shift.setStatus("REJECTED");
        return shiftRepository.save(shift);
    }

    @Override
    public List<UserBranchShift> getBranchShifts(Long branchId) {
        return shiftRepository.findByBranchId(branchId);
    }
}

