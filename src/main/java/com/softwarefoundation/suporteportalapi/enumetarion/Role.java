package com.softwarefoundation.suporteportalapi.enumetarion;

import com.softwarefoundation.suporteportalapi.config.security.Authority;

public enum Role {
    ROLE_USER(Authority.getUserAuthorities()),
    ROLE_HR(Authority.getHrAuthorities()),
    ROLE_MANAGER(Authority.getManagerAuthorities()),
    ROLE_ADMIN(Authority.getAdminAuthorities()),
    ROLE_SUPER_USER(Authority.getSuperUserAuthorities());

    private String[] authorities;

    Role(String... authorities){
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
