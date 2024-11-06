/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.service;

import com.store.store.models.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author fernan
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    
    private static final String SECRET_KEY = "my.secret.key";

    public String getToken(UserDetails users) {
        return getTokenUser(new HashMap<>(), users); //clase de colecciones que se usa para almacenar pares de clave y valor
         
    }

    //creamos el token
    private String getTokenUser(HashMap<String, Object> hashMap, UserDetails users) {
        return Jwts
                .builder()
                .setClaims(hashMap)
                .setSubject(users.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    //Decodificamos nuestra secretKey
    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
