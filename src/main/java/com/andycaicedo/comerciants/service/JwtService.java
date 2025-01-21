package com.andycaicedo.comerciants.service;

import static com.andycaicedo.comerciants.configuration.Constans.*;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.andycaicedo.comerciants.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    public String getToken(User user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, User user) {
        extraClaims.put("user_id", user.getId());
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + token;
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SUPER_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
        return getClaim(token, claims -> (Long) claims.get("user_id"));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
