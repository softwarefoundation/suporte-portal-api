package com.softwarefoundation.suporteportalapi.listner;

import com.softwarefoundation.suporteportalapi.domain.UserPrincipal;
import com.softwarefoundation.suporteportalapi.resource.LoginAttempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListner {

    private LoginAttempService loginAttempService;

    @Autowired
    public AuthenticationSuccessListner(LoginAttempService loginAttempService) {
        this.loginAttempService = loginAttempService;
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event){
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String){
            UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttempService.evictUserFromLoginAttempCache(user.getUsername());
        }
    }
}
