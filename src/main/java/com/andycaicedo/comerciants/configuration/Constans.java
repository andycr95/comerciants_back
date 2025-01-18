package com.andycaicedo.comerciants.configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.security.Keys;

public class Constans {
    public static final String LOGIN_URL = "/login";
    public static final long TOKEN_EXPIRATION_TIME = 86400000;
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    public static final String SUPER_SECRET_KEY = "ZdaXc3RhZXN1bmFmcmFzZXNlY3JlZXRhcGFyYWp3dAcnVlYmFkZWVgYGFyYWJhc2U2M=";



    public static Key getSigningKey(String secret) {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
} 