package uk.cf.ac.LegalandGeneralTeam11.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Properties;

@Configuration
public class MailSenderConfig {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    public MailSenderConfig(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("abushvin@gmail.com");
        mailSender.setPassword("msta vadi mjis kbrl");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public String processTemplate(String templateName, Context context) {
        return templateEngine.process(templateName, context);
    }
}