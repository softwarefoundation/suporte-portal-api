package com.softwarefoundation.suporteportalapi.service.impl;

import com.softwarefoundation.suporteportalapi.config.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl{

    //@Qualifier("gmailConfigurationsProperties")
    @Autowired
    private JavaMailSender emailSender;


    /**
     *
     * @param nome
     * @param to
     * @param password
     */
    public void sendEmailNewPassword(String nome, String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EmailConstant.FROM_EMAIL);
        message.setTo(to);
        message.setSubject(EmailConstant.EMAIL_SUBJECT);
        message.setText(String.format("Ola, %s \n\n\n Sua nova senha: %s \n\n\n Support Team",nome,password));
        emailSender.send(message);
        log.info("Enviando e-mail para: {}",to);
    }


}
