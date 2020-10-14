package com.softwarefoundation.suporteportalapi.service.impl;

import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import com.softwarefoundation.suporteportalapi.domain.Usuario;
import com.softwarefoundation.suporteportalapi.enumetarion.Role;
import com.softwarefoundation.suporteportalapi.exceptions.UserNotFoundException;
import com.softwarefoundation.suporteportalapi.exceptions.UsernameExistException;
import com.softwarefoundation.suporteportalapi.repository.UsuarioRepository;
import com.softwarefoundation.suporteportalapi.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Slf4j
@Service
@Qualifier("UserDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Pesquisando usuário por nome: {}", username);
        Usuario usuario = usuarioRepository.findUsuarioByNome(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado pelo nome: " + username);
        } else {
            usuario.setDataUltimoLoginDisplay(usuario.getDataUltimoLoginDisplay());
            usuario.setDataUltimoLogin(new Date());
            usuarioRepository.save(usuario);
            UserPrincipal userPrincipal = new UserPrincipal(usuario);
            log.info("Usuário entrado: {}", username);
            return userPrincipal;
        }
    }

    @Override
    public Usuario register(String nome, String sobrenome, String username, String email) throws UsernameExistException, UserNotFoundException {
        validateNewUsernameAndEmail("", nome, email);
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(generateUserId());
        String password = generatePassword();
        String encodePassword = encodePassword(password);
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setDataCadastro(new Date());
        usuario.setSenha(encodePassword);
        usuario.setAtivo(true);
        usuario.setBloqueado(false);
        usuario.setRoles(Role.ROLE_USER.name());
        usuario.setAuthorities(Role.ROLE_USER.getAuthorities());
        usuario.setUrlImagemPerfil(getTemporaryProfileImageUrl());
        usuarioRepository.save(usuario);
        log.info("Senha:{}", password);
        return usuario;
    }

    private String getTemporaryProfileImageUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString();
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String generatePassword() {
        List<String> letras = Arrays.asList("A", "X", "W", "B", "O");
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            senha.append(letras.get(new Random().nextInt(letras.size() - 1)));
        }
        return senha.toString();
    }

    private String generateUserId() {
        StringBuilder usuarioId = new StringBuilder();
        do {
            usuarioId.append(Integer.valueOf(new Random().nextInt(9)));
        } while (usuarioId.length() < 10);
        return usuarioId.toString();
    }

    private Usuario validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException {
        Usuario usuarioByNewUsername = findByUsername(newUsername);
        Usuario usuarioByNewEmail = findByEmail(newEmail);
        if (currentUsername != null && !currentUsername.isEmpty()) {
            Usuario currentUser = usuarioRepository.findUsuarioByNome(currentUsername);
            if (Objects.isNull(currentUser)) {
                throw new UserNotFoundException("Usuário não encontrado pelo nome: " + currentUsername);
            }
            if (Objects.nonNull(usuarioByNewUsername) && usuarioByNewEmail.equals(currentUser)) {
                throw new UsernameExistException("Email já cadastrado");
            }
            return currentUser;
        } else {
            if (Objects.nonNull(usuarioByNewUsername)) {
                throw new UsernameExistException("Usuário já cadastrado");
            }
            if (Objects.nonNull(usuarioByNewEmail)) {
                throw new UsernameExistException("Email já cadastrado");
            }
            return null;
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findUsuarioByNome(username);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }
}
