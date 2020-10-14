package com.softwarefoundation.suporteportalapi.config.security;

import java.util.HashSet;
import java.util.Set;

public class Authority {

    public static final String[] getUserAuthorities(){
        Set<String> authorities = new HashSet<>();
        authorities.add("user:read");
        return authorities.toArray(new String[authorities.size()]);
    }

     public static final String[] getHrAuthorities(){
        Set<String> authorities = new HashSet<>();
        authorities.add("user:read");
        authorities.add("user:update");
        return authorities.toArray(new String[authorities.size()]);
    }

    public static final String[] getManagerAuthorities(){
        Set<String> authorities = new HashSet<>();
        authorities.add("user:read");
        authorities.add("user:update");
        return authorities.toArray(new String[authorities.size()]);
    }

    public static final String[] getAdminAuthorities(){
        Set<String> authorities = new HashSet<>();
        authorities.add("user:read");
        authorities.add("user:create");
        authorities.add("user:update");
        return authorities.toArray(new String[authorities.size()]);
    }

    public static final String[] getSuperUserAuthorities(){
        Set<String> authorities = new HashSet<>();
        authorities.add("user:read");
        authorities.add("user:create");
        authorities.add("user:update");
        authorities.add("user:delete");
        return authorities.toArray(new String[authorities.size()]);
    }



}
