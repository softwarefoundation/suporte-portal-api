package com.softwarefoundation.suporteportalapi.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "TB01_USUARIO")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USUARIO_ID")
    private String usuarioId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SOBRENOME")
    private String sobrenome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "URL_IMAGEM_PERFIL")
    private String urlImagemPerfil;

    @Column(name = "DATA_ULTIMO_LOGIN")
    private Date dataUltimoLogin;

    @Column(name = "DATA_ULTIMO_LOGIN_DISPLAY")
    private Date dataUltimoLoginDisplay;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;
    /**
     * delete, update, create
     */
    @Column(name = "ROLES")
    private String roles;

    @Column(name = "AUTHORITIES")
    private String[] authorities;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "BLOQUEADO")
    private Boolean bloqueado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
