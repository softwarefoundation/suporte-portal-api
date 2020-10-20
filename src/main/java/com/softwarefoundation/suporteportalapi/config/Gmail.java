package com.softwarefoundation.suporteportalapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static com.softwarefoundation.suporteportalapi.config.EmailConstant.SMTP_STARTTLS_REQUIRED;

//@Component
public class Gmail {

   // @Bean
    public JavaMailSender gmailConfigurationsProperties() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put(EmailConstant.SMTP_AUTH, "true");
        props.put(EmailConstant.SMTP_STARTTLS_ENABLE, "true");
        props.put(EmailConstant.SMTP_STARTTLS_REQUIRED, "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
