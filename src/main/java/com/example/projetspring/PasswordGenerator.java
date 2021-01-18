package com.example.projetspring;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = "1234";
        String encoded = encoder.encode(pwd);
        System.out.println(encoded);
    }
}
