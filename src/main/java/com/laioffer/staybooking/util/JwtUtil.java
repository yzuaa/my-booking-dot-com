package com.laioffer.staybooking.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {//创建token

    @Value("${jwt.secret}")//spring会帮你从application.properties里找key 然后把值赋过来
    private String secret;

    //subject == username
    public String generateToken(String subject) {
        return Jwts.builder()
                .setClaims(new HashMap<>())//Claims == HashMap
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, secret)//加密alg
                .compact();
    }

    private Claims extractClaims(String token) {
        //setSigningKey(secret)解密时通过加密时的secret再encode HashMap,如果无法ecnode出HM，则reject
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public Boolean validateToken(String token) {
        return extractExpiration(token).after(new Date());
    }

}
