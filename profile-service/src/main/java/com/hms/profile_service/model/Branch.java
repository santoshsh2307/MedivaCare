package com.hms.profile_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "branch")
@Data
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String branchName;

    private String location;

    private String status = "ACTIVE"; // ACTIVE / INACTIVE

    // ✅ HOSPITAL, BRANCH, DEPARTMENT, UNIT
    private String type;

    // ✅ Self Join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Branch parent;

    @OneToMany(mappedBy = "parent")
    private List<Branch> children;
}
