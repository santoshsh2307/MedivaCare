package com.hms.profile_service.service;

import com.hms.profile_service.model.UserBranchShift;

import com.hms.profile_service.dto.UserBranchShiftRequest;
import com.hms.profile_service.model.UserBranchShift;

import java.util.List;

public interface UserBranchShiftService {

    List<UserBranchShift> getUserShifts(Long userId);

    UserBranchShift submitShiftRequest(Long userId, UserBranchShiftRequest request);

    UserBranchShift approveShift(Long shiftId);

    UserBranchShift rejectShift(Long shiftId);

    List<UserBranchShift> getBranchShifts(Long branchId);
}

