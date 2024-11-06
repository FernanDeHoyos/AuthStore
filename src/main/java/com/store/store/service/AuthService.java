/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.service;


import com.store.store.controller.AuthResponse;
import com.store.store.controller.LoginResponse;
import com.store.store.controller.RegisterResponse;
import com.store.store.models.Roles;
import com.store.store.models.Users;
import com.store.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author fernan
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    public AuthResponse login(LoginResponse request){
        return null;
    }
    
    public AuthResponse register(RegisterResponse request){
        Users users = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Roles.USER)
                .build();
        
        userRepository.save(users);
        
        return AuthResponse.builder()
               .token(jwtService.getToken(users))
               .build();
    }
}


