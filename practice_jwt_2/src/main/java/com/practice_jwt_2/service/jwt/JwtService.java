package com.practice_jwt_2.service.jwt;

import com.practice_jwt_2.model.jwt.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "832471094219310493859149409740195739348";
    private static final long EXPIRES_TIME = 86400000L;

    public String generateToken(Authentication auth) {
        UserPrinciple userPrinciple = (UserPrinciple) auth.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + EXPIRES_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token Message: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token Message: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token Message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty --> Message: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
