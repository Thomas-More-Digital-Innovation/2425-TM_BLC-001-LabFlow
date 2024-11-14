package com.thomasmore.blc.labflow.service;
// service voor het genereren van JWT
import com.thomasmore.blc.labflow.entity.User;
import com.thomasmore.blc.labflow.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    private final UserRepository userRepository;
    private String secretKey = "";

    // constructor voor het genereren van een JWT key
    public JWTService(UserRepository userRepository) {
        try {
            // aanmaken instance KeyGenerator met algoritme HMACSHA256
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            // gegenereerde key omvormen naar Base64 om JWT te ondertekenen
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {

        // claims zijn extra gegevens die meegegeven kunnen worden met een JWT
        Map<String, Object> claims = new HashMap<>();

        String rol = Objects.requireNonNull(userRepository.findByEmail(user.getEmail()).getRol()).getNaam();
        String userId = Objects.requireNonNull(userRepository.findByEmail(user.getEmail()).getId()).toString();
        claims.put("rol", rol);
        claims.put("userId", userId);

        // https://javadoc.io/doc/io.jsonwebtoken/jjwt-api/0.11.2/io/jsonwebtoken/JwtBuilder.html
        // voor debug van token: https://jwt.io/
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // na 30 minuten vervalt de token
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        // HMACSHA algoritme heeft input van bytes nodig
        // conversie van key naar bytes
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // methodes voor JwtFilter
    // claims:: is een manier om een methode aan te roepen zonder een instantie ervan aan te maken
    // claims.get... werkt alleen op een object

    // Haalt het emailadres op uit het JWT-token
    public String extractEmail(String token) {
        // Roept extractClaim aan om het onderwerp (email) uit de claims te halen
        return extractClaim(token, Claims::getSubject);
    }

    // methode om claims uit een JWT-token te extraheren
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        // Past de claimsResolver functie toe om de gewenste claim te extraheren
        return claimsResolver.apply(claims);
    }

    // Methode om alle claims uit het JWT-token te extraheren
    private Claims extractAllClaims(String token) {
        // parsen van token en verifieren met de getKey() methode
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Methode om JWT-token te valideren
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        // Controleert of het emailadres overeenkomt en of de token niet is verlopen
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Controleert of het JWT-token is verlopen
    private boolean isTokenExpired(String token) {
        // Vergelijkt de vervaldatum van het token met de huidige datum
        return extractExpiration(token).before(new Date());
    }

    // Haalt de vervaldatum uit het JWT-token
    private Date extractExpiration(String token) {
        // extractClaim haalt de vervaldatum uit de claim
        return extractClaim(token, Claims::getExpiration);
    }
}
