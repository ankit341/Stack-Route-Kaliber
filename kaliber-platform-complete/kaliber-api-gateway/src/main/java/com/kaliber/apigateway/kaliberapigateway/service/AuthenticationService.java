package com.kaliber.apigateway.kaliberapigateway.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class AuthenticationService {

    static String jwtSigningKey;

    @Value("${jwt.signing.key}")
    public void setJwtSigningKey(String jwtSigningKey) {
        AuthenticationService.jwtSigningKey = jwtSigningKey;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    try {
                        String user = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(cookie.getValue()).getBody()
                                .get("email", String.class);
                        if (user != null) {
                            System.out.println("user not null");
                            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                        }
                    } catch (ExpiredJwtException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}