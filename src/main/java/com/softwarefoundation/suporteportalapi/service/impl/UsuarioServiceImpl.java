package com.softwarefoundation.suporteportalapi.service.impl;

import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import com.softwarefoundation.suporteportalapi.domain.Usuario;
import com.softwarefoundation.suporteportalapi.repository.UsuarioRepository;
import com.softwarefoundation.suporteportalapi.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@Qualifier("UserDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Pesquisando usuário por nome: {}",username);
        Usuario usuario = usuarioRepository.findUsuarioByNome(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado pelo nome: "+username);
        }else{
            usuario.setDataUltimoLoginDisplay(usuario.getDataUltimoLoginDisplay());
            usuario.setDataUltimoLogin(new Date());
            usuarioRepository.save(usuario);
            UserPrincipal  userPrincipal = new UserPrincipal(usuario);
            log.info("Usuário entrado: {}", username);
            return userPrincipal;
        }
    }
}
