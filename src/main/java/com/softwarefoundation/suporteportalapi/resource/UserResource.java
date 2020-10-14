package com.softwarefoundation.suporteportalapi.resource;

import com.softwarefoundation.suporteportalapi.domain.Usuario;
import com.softwarefoundation.suporteportalapi.exceptions.ExceptionHandling;
import com.softwarefoundation.suporteportalapi.exceptions.UserNotFoundException;
import com.softwarefoundation.suporteportalapi.exceptions.UsernameExistException;
import com.softwarefoundation.suporteportalapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserResource extends ExceptionHandling {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(path = "/home")
    public String showUser(){
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

}
