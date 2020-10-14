package com.softwarefoundation.suporteportalapi.service;

import com.softwarefoundation.suporteportalapi.domain.Usuario;
import com.softwarefoundation.suporteportalapi.exceptions.UserNotFoundException;
import com.softwarefoundation.suporteportalapi.exceptions.UsernameExistException;

import java.util.List;

public interface UsuarioService {

    Usuario register(String nome, String sobrenome, String username, String email) throws UsernameExistException, UserNotFoundException;

    List<Usuario> getUsuarios();

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);

}
