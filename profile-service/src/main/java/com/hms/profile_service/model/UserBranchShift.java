package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "user_branch_shift")
@Data
@NoArgsConstructor
public class UserBranchShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalTime startTime;
    private LocalTime endTime;

    @Column(name = "days_of_week") // MON,TUE,FRI
    private String daysOfWeek;

    @Column(name = "on_call")
    private boolean onCall = false;

    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED
}

