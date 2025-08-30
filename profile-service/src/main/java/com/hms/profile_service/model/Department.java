package com.hms.profile_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data                       // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor          // Generates no-args constructor
@AllArgsConstructor         // Generates all-args constructor
@Builder                    // Enables Builder pattern
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String status;
}

