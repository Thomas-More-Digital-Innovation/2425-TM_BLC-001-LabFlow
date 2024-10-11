package com.thomasmore.blc.labflow.service;
// service voor het genereren van JWT
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private String secretKey = "";

    // constructor voor het genereren van een JWT key
    public JWTService() {
        try {
            // aanmaken instance KeyGenerator met algoritme HMACSHA256
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            // gegenereerde key omvormen naar Base64 om JWT te ondertekenen
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email) {

        // claims zijn extra gegevens die meegegeven kunnen worden met een JWT
        // momenteel is deze leeg, maar kan gebruikt worden om extra info in token te stoppen
        Map<String, Object> claims = new HashMap<>();

        // https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/0.11.2/io/jsonwebtoken/JwtBuilder.html
        // voor debug van token: https://jwt.io/
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutes
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        // HMACSHA algoritme heeft input van bytes nodig
        // conversie van key naar bytes
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
