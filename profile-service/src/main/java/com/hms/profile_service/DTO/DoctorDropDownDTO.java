package com.hms.profile_service.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorDropDownDTO {
    private Long id;
    private String username;
    private String name;

    public DoctorDropDownDTO(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }
}
