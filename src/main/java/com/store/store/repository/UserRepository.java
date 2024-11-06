/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.repository;

import com.store.store.models.Users;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author fernan
 */
public interface UserRepository extends JpaRepository<Users, Long>{

    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
