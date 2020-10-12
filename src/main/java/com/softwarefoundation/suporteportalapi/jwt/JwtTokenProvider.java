package com.softwarefoundation.suporteportalapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.softwarefoundation.suporteportalapi.config.security.SecurityConstants;
import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    /**
     *
     * @param userPrincipal
     * @return
     */
    public String generateJwtToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);

        return JWT.create()
                .withIssuer(SecurityConstants.GET_ARAYS_LLC)
                .withAudience(SecurityConstants.GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstants.AUTHORITIES, claims)
                .withExpiresAt(new Date(Long.sum(System.currentTimeMillis(),SecurityConstants.EXPIRATION_TIME)))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    /**
     *
     * @param userPrincipal
     * @return
     */
    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();
        userPrincipal.getAuthorities().forEach( a-> authorities.add(a.getAuthority()));
        return authorities.toArray(new String[0]);
    }


}
