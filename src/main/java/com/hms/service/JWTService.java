package com.hms.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    // @Value annotation fetches the values from the application.properties file.
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;
//  this comes from the JWT.
    private Algorithm algorithm;

//  @PostConstruct -  This method is capable of running itself without calling it. this annotation
//                    comes from Hibernate. it will run when project is started.
    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(String username){
//        This one line will generate JWT token.
//        When to generate token, when login is successful.
        return JWT.create()
                .withClaim("name", username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }
    public String getUsername(String token){
        DecodedJWT decodedJwt =
                JWT.require(algorithm).
                withIssuer(issuer).
                build().
                verify(token);
        return decodedJwt.getClaim("name").asString();

    }



}
