package com.hms.profile_service.serviceImpl;


import com.hms.profile_service.dto.StaffOnboardingRequest;
import com.hms.profile_service.dto.StaffOnboardingResponse;
import com.hms.profile_service.exception.DuplicateResourceException;
import com.hms.profile_service.model.User;
import com.hms.profile_service.model.Role;
import com.hms.profile_service.repository.UserRepository;
import com.hms.profile_service.repository.RoleRepository;
import com.hms.profile_service.repository.DepartmentRepository;
import com.hms.profile_service.service.StaffOnboardingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StaffOnboardingServiceImpl implements StaffOnboardingService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffOnboardingServiceImpl(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      DepartmentRepository departmentRepository,
                                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<StaffOnboardingResponse> onboard(String performedBy, StaffOnboardingRequest request) {
        return Mono.fromCallable(() -> {
                    validateRequest(request);

                    String username = safe(() -> request.getSystemAccess().getUsername());
                    String phone = safe(() -> request.getPersonalInfo().getPhone());

                    // duplicates check (blocking -> wrapped below)
                    if (userRepository.existsByUsername(request.getSystemAccess().getUsername())) {
                        throw new DuplicateResourceException("Username already exists: " + username);
                    }
                    if (phone != null && userRepository.existsByPhoneNumber(phone)) {
                        throw new DuplicateResourceException("Phone already exists: " + phone);
                    }

                    // map DTO -> entity (simple mapping)
                    User user = new User();
                    user.setUsername(request.getSystemAccess().getUsername());
                    // generate/encode temporary password
                    user.setPassword(passwordEncoder.encode("adminpass"));

                    if (request.getPersonalInfo() != null) {
                        user.setFullName(request.getPersonalInfo().getFullName());
                        user.setPhoneNumber(request.getPersonalInfo().getPhone());
                        user.setDob(request.getPersonalInfo().getDob());
                        user.setGender(request.getPersonalInfo().getGender());
                        user.setAddress(request.getPersonalInfo().getAddress());
                    }

                    if (request.getProfessionalInfo() != null) {
                        user.setDesignation(request.getProfessionalInfo().getDesignation());
                        user.setExperienceYears(request.getProfessionalInfo().getExperienceYears());
                        user.setQualification(request.getProfessionalInfo().getQualification());
                        user.setSpecialization(request.getProfessionalInfo().getSpecialization());
                        user.setShift(request.getProfessionalInfo().getShift());
                        user.setDepartmentId(Long.valueOf(request.getProfessionalInfo().getDepartment()));

                        // try to resolve department name -> departmentId (optional)
                        if (request.getProfessionalInfo().getDepartment() != null) {
                            departmentRepository.findByName(request.getProfessionalInfo().getDepartment())
                                    .ifPresent(dept -> user.setDepartmentId(dept.getId()));
                            // If department is required and not found, you may want to throw here.
                        }
                    }

                    if (request.getComplianceAndDocs() != null) {
                        user.setAadharNumber(request.getComplianceAndDocs().getAadharNumber());
                        user.setPanNumber(request.getComplianceAndDocs().getPanNumber());
                        user.setMedicalLicenseNo(request.getComplianceAndDocs().getMedicalLicenseNo());
                    }

                    if (request.getReviewAndConsent() != null) {
                        user.setTermsAccepted(request.getReviewAndConsent().getTermsAccepted());
                    }

                    // assign roles if provided (roleRepository is blocking)
                    if (request.getSystemAccess() != null && request.getSystemAccess().getRoleIds() != null) {
                        Set<Role> roles = StreamSupport
                                .stream(roleRepository.findAllById(request.getSystemAccess().getRoleIds()).spliterator(), false)
                                .collect(Collectors.toSet());
                        user.setRoles(roles);
                    }

                    // finally save (blocking save)
                    User saved = userRepository.save(user);
                    return new StaffOnboardingResponse( "Onboarding successful. Temporary password sent/generated.","username");
                })
                // Important: run DB work on boundedElastic so event-loop isn't blocked
                .subscribeOn(Schedulers.boundedElastic())
                // Map DB exceptions if you want; leave them be to the controller to convert
                ;
    }

    @Override
    public Flux<User> getAllActiveUsers() {
        List<User> users = userRepository.findByEnabledTrue();
        return Flux.fromIterable(users);
    }

    private void validateRequest(StaffOnboardingRequest req) {
        if (req == null) throw new IllegalArgumentException("Request body is required");
        if (req.getSystemAccess() == null || req.getSystemAccess().getUsername() == null || req.getSystemAccess().getUsername().isBlank())
            throw new IllegalArgumentException("systemAccess.username is required");
        if (req.getPersonalInfo() == null || req.getPersonalInfo().getFullName() == null || req.getPersonalInfo().getFullName().isBlank())
            throw new IllegalArgumentException("personalInfo.fullName is required");
    }

    private <T> T safe(SupplierWithException<T> supplier) {
        try { return supplier.get(); } catch (Exception e) { return null; }
    }

    private String generateTempPassword() {
        // 12-char random (simple). Consider emailing it or returning via secure channel.
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    @FunctionalInterface
    private interface SupplierWithException<T> { T get() throws Exception; }
}

