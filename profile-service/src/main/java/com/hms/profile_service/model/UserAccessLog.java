package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_access_log")
@Data
@NoArgsConstructor
public class UserAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "access_mode") // NORMAL, EMERGENCY, ON_CALL
    private String accessMode;

    private String reason;

    private LocalDateTime accessTime = LocalDateTime.now();
}
