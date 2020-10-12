package com.softwarefoundation.suporteportalapi.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Usuario implements Serializable {

    private Long id;
    private String usuarioId;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String urlImagemPerfil;
    private Date dataUltimoLogin;
    private Date dataUltimoLoginDisplay;
    private Date dataCadastro;
    /**
     * delete, update, create
     */
    private String[] roles;
    private String[] authorities;
    private Boolean ativo;
    private Boolean bloqueado;
}
