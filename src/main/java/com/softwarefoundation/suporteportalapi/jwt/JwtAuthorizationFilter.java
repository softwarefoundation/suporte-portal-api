package com.softwarefoundation.suporteportalapi.jwt;

import com.softwarefoundation.suporteportalapi.config.security.SecurityConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(SecurityConstants.OPTIONS_HHTP_METHODS)){
            response.setStatus(HttpStatus.OK.value());
        }else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)){
                filter.doFilter(request,response);
                return;
            }
            String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);
            if(jwtTokenProvider.isTokenValido(username, token) && SecurityContextHolder.getContext().getAuthentication() == null){
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
        }
        filter.doFilter(request,response);
    }
}
