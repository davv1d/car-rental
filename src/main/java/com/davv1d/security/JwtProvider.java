package com.davv1d.security;

import com.davv1d.security.user.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.davv1d.security.JwtProperties.EXPIRATION;
import static com.davv1d.security.JwtProperties.SECRET;

@Component
public class JwtProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT Token -> Message: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature -> Message: {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Jwt claims string is empty -> Message: {}", e.getMessage());
        }
        return false;
    }
}
