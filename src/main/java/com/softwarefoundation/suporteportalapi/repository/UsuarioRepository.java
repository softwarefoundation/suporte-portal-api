package com.softwarefoundation.suporteportalapi.repository;

import com.softwarefoundation.suporteportalapi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByNome(String username);

    Usuario findUsuarioByEmail(String email);

}
