package com.softwarefoundation.suporteportalapi.config.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SecurityConstants {

    /**
     * 5 dias de validade do token
     */
    public static final long EXPIRATION_TIME = 432_000_000;

    public static final String TOKEN_PREFIX = "Bearer";

    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    public static final String TOKEN_CANNOT_BE_VERIFY = "Token não pode ser verificado";

    public static final String GET_ARAYS_LLC = "Get Arrays LLC";

    public static final String GET_ARRAYS_ADMINISTRATION = "Get Arrays Administration";

    public static final String AUTHORITIES = "Authorities";

    public static final String FORBIDDEN_MESSAGE = "Faça o login para acessar essa página";

    public static final String ACCESS_DENIED_MESSAGE = "Você não tem permissão para acessar essa página";

    public static final String OPTIONS_HHTP_METHODS = "OPTIONS";

    /**
     *
     *
     * @return
     */
    public static final String[] getPublicUrls(){
        HashSet<String> publicUrl = new HashSet<>();
        publicUrl.add("/user/login");
        publicUrl.add("/user/registter");
        publicUrl.add("/user/resetpassword/**");
        publicUrl.add("/user/image/**");
        publicUrl.add("/user/**");
        return publicUrl.toArray(new String[publicUrl.size()]);
    }

}
