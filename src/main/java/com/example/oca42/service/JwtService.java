package com.example.oca42.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String AUTHORITIES_KEY = "roles";
    private final String secretKey = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";

    /**
     * Generate JWT token with username and authorities
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1h
                .signWith(getSecretKeySpec())
                .compact();
    }

    /**
     * Extract username
     */
    public String extractUsername(String token) {
        return parseClaims(token).getPayload().getSubject();
    }

    /**
     * Extract authorities
     */
    public List<String> extractAuthorities(String token) {
        Object authorities = parseClaims(token).getPayload().get(AUTHORITIES_KEY);
        if (authorities instanceof List<?>) {
            return ((List<?>) authorities).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Validate token against user details
     */
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }

//    private boolean isTokenExpired(String token) {
//        return parseClaims(token).getPayload().getExpiration().before(new Date());
//    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKeySpec())
                .build()
                .parseSignedClaims(token);
    }


    public SecretKey getSecretKeySpec() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

}
