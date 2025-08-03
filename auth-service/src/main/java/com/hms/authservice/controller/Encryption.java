package com.hms.authservice.controller;

public class Encryption {
    public static void main(String[] args) {
        String rawPassword = "adminpass";
        String encoded = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("Encoded password: " + encoded);
    }

}
