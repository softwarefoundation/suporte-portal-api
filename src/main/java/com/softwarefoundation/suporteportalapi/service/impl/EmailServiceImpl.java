package com.softwarefoundation.suporteportalapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl{

    @Autowired
    private JavaMailSenderImpl emailSender;

    @Value("${app.email.enviar-email-de-cadastro}")
    private Boolean isEnviarEmailCadastro;


    /**
     *
     * @param nome
     * @param to
     * @param password
     */
    public void sendEmailNewPassword(String nome, String to, String password) {
        if(!isEnviarEmailCadastro){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        JavaMailSenderImpl sender = getMailSender(emailSender);
        message.setFrom(sender.getUsername());
        message.setTo(to);
        message.setSubject("Software Foundation, LLC - New Password");
        message.setText(String.format("Ola, %s \n\n\n Sua nova senha: %s \n\n\n Support Team",nome,password));
        log.info("Enviando e-mail para: {}",to);
        sender.send(message);
    }

    /**
     *
     * @param emailSender
     * @return
     */
    private JavaMailSenderImpl getMailSender(JavaMailSender emailSender){
        JavaMailSenderImpl javaMailSender = null;
        if(emailSender instanceof JavaMailSenderImpl){
            javaMailSender = (JavaMailSenderImpl) emailSender;
        }
        return javaMailSender;
    }

}
