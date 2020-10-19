package com.softwarefoundation.suporteportalapi.listner;

import com.softwarefoundation.suporteportalapi.resource.LoginAttempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListner {

    private LoginAttempService loginAttempService;

    @Autowired
    public AuthenticationFailureListner(LoginAttempService loginAttempService) {
        this.loginAttempService = loginAttempService;
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event){
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String){
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttempService.adaUserToLoginAttempCache(username);
        }
    }
}
