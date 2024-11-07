/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.service;

import com.store.store.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author fernan
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    
     @Value("${jwt.secret.key}")
    private String SECRET_KEY;
     
    @Value("${jwt.expiration.time}")
    private long expirationTime;

    public String getToken(UserDetails users) {
        return getTokenUser(new HashMap<>(), users); //clase de colecciones que se usa para almacenar pares de clave y valor
         
    }

    //creamos el token
    public String getTokenUser(HashMap<String, Object> hashMap, UserDetails users)  {
        return Jwts
                .builder()
                .setClaims(hashMap)
                .setSubject(users.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Decodificamos nuestra secretKey
    public Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean IsValidToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
    
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }
    
    private boolean isExpired(String token){
        return getExpiration(token).before(new Date());
    }
    
}
