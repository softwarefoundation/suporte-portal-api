package com.softwarefoundation.suporteportalapi.resource;

import com.softwarefoundation.suporteportalapi.exceptions.ExceptionHandling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserResource extends ExceptionHandling {

    @GetMapping(path = "/home")
    public String showUser(){
        return "Application works!";
    }


}
