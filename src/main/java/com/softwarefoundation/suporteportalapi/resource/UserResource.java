package com.softwarefoundation.suporteportalapi.resource;

import com.softwarefoundation.suporteportalapi.config.security.SecurityConstants;
import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import com.softwarefoundation.suporteportalapi.domain.Usuario;
import com.softwarefoundation.suporteportalapi.exceptions.ExceptionHandling;
import com.softwarefoundation.suporteportalapi.exceptions.UserNotFoundException;
import com.softwarefoundation.suporteportalapi.exceptions.UsernameExistException;
import com.softwarefoundation.suporteportalapi.jwt.JwtTokenProvider;
import com.softwarefoundation.suporteportalapi.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/user")
public class UserResource extends ExceptionHandling {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping(path = "/home")
    public String showUser() {
        return "Application works!";
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) throws UsernameExistException, UserNotFoundException {
        Usuario us = usuarioService.register(usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getUsername(),
                usuario.getEmail());
        return new ResponseEntity(us, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        log.info("Login: {} - {}",usuario.getUsername(),usuario.getSenha());
        authentication(usuario.getUsername(), usuario.getSenha());
        Usuario loginUser = usuarioService.findByUsername(usuario.getUsername());
        UserPrincipal principal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(principal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal principal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(principal));
        log.info("JWT Token: {}",headers.toString());
        return headers;
    }

    private void authentication(String username, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,senha));
    }

}
