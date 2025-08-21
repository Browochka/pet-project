package com.browka.bankinglearning.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private String secret;
    public JWTService() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keygen.generateKey();
            this.secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
throw new RuntimeException(e);
        }
    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token, Function<Claims,T> getSubject) {
        final Claims claims =extractAllClaims(token);
        return getSubject.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<String,Object>();
        return Jwts.builder().claims().add(claims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*60))
        .and().
                signWith(getKey()).
                compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
