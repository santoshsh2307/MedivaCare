package com.hms.authservice.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.hms.authservice.config.JwtUtils;
import com.hms.authservice.dto.AuthRequest;
import com.hms.authservice.model.User;
import com.hms.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository=userRepository;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
//        System.out.println(">>> Login called with username: " + request.getUsername());
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String token = jwtUtils.generateToken(userDetails);
//
//            userRepository.findByUsername(userDetails.getUsername()).ifPresent(user -> {
//                user.setLastLoginAt(LocalDateTime.now());
//                userRepository.save(user);
//            });
//            return ResponseEntity.ok(Map.of("jwt", token));
//        } catch (Exception e) {
//            System.out.println(">>> Authentication failed: " + e.getMessage());
//            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println(">>> Login called with username: " + request.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // update last login
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user.getUsername());
            userData.put("username", user.getUsername());
            userData.put("roles", user.getRoles());
            userData.put("branchAccess", "[{\"branchId\":\"1\",\"branchName\":\"city branch main\"},{\"branchId\":\"2\",\"branchName\":\"city branch east\"}]");
            //userData.put("branchName", user.getBranchName());

            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("user", userData);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println(">>> Authentication failed: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "status", HttpStatus.UNAUTHORIZED.value(),
                            "message", "Invalid username or password"
                    )
            );
        }
    }


}
