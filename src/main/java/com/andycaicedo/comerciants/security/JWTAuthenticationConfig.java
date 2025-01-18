package com.andycaicedo.comerciants.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static com.andycaicedo.comerciants.configuration.Constans.*;

@Configuration
public class JWTAuthenticationConfig {
    
    public String getJwtToken(String email) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
            .builder()
            .setId("andycaicedoComerciants")
            .setSubject(email)
            .claim("authorities", 
                    grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512).compact();

        return "Bearer " + token;
    }
}
