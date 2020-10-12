package com.softwarefoundation.suporteportalapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.softwarefoundation.suporteportalapi.config.security.SecurityConstants;
import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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

    /**
     *
     * @param token
     * @return
     */
    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     *
     * @param token
     * @return
     */
    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
    }

    /**
     *
     * @return
     */
    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstants.GET_ARAYS_LLC).build();
        }catch (Exception e){
            throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFY);
        }
        return verifier;
    }

    /**
     *
     * @param username
     * @param authorities
     * @param request
     * @return
     */
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    /**
     *
     * @param username
     * @param token
     * @return
     */
    public boolean isTokenValido(String username, String token){
        JWTVerifier verifier = getJwtVerifier();
        return (username != null && !username.isEmpty()) && !isTokenExpirado(verifier,token);
    }

    /**
     *
     * @param verifier
     * @param token
     * @return
     */
    private boolean isTokenExpirado(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    /**
     * 
     * @param token
     * @return
     */
    public String getSubject(String token){
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

}
