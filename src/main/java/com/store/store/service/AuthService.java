/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.service;

import com.store.store.controller.AuthResponse;
import com.store.store.controller.LoginResponse;
import com.store.store.controller.RegisterResponse;
import com.store.store.errorConfig.DuplicateResourceException;
import com.store.store.models.Roles;
import com.store.store.models.Users;
import com.store.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author fernan
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    
    public AuthResponse login(LoginResponse request) {
     // Intentar autenticar al usuario con el AuthenticationManager 
       
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        // Buscar el usuario en la base de datos
        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String token = jwtService.getToken(user);  // Generar el token JWT
        return AuthResponse.builder()  // Retornar la respuesta con el token generado
                .token(token)
                .build();
}

    
    

    public AuthResponse register(RegisterResponse request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // Encriptar la contraseña

        // Verifica si el username ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario ya existe");
        }
        // Verifica si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("El email ya está registrado");
        }
        Users users = Users.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname()) // Corregir el nombre del campo
                .age(request.getAge())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encodedPassword) // Usar la contraseña encriptada
                .role(Roles.USER)
                .build();

        userRepository.save(users);

        return AuthResponse.builder()
                .token(jwtService.getToken(users))
                .build();
    }

}
