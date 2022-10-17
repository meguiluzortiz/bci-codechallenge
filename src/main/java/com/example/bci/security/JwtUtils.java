package com.example.bci.security;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private final JwtParser jwtParser;

    public String generateJwtToken(Authentication authentication) {

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        return Jwts.builder() //
                .setSubject(username) //
                .setIssuedAt(new Date()) //
                .setExpiration(new Date(new Date().getTime() + jwtProperties.getExpiration())) //
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()) //
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        var parser = jwtParser.setSigningKey(jwtProperties.getSecret());
        var claims = parser.parseClaimsJws(token);
        return claims.getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            getAllClaimsFromToken(authToken);

            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}

