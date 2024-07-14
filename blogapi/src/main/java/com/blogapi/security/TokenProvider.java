package com.blogapi.security;

import com.blogapi.exceptions.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {


    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;
    public String generateToken(Authentication authentication){

        String userName = authentication.getName();
       Date currentDate = new Date();
        Date expiryDate = new Date((currentDate.getTime() + jwtExpirationInMs));

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();

        return token;

    }

    public boolean validateToken(String token) throws BlogAPIException {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

        }catch (SignatureException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }

    }

    public String getUserNameFromJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String userName = claims.getSubject();
        return userName;
    }


}
