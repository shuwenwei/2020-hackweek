package com.sww.util;

import com.sww.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author sww
 */
public class JwtUtil {

    private JwtUtil(){
    }

    private static final long EXPIRE_TIME = 2*60*60*1000;
    private static final String SECRET_KEY = "139uaj13a4ga-0139f1";

    public static String generateToken(User user){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setId(user.getId().toString())
                .setSubject(user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public static User getUser(String token){
        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (RuntimeException e){
            return null;
        }
        User user = new User();
        user.setId(Long.valueOf(body.getId()));
        user.setRole(body.getSubject());
        return user;
    }

}
