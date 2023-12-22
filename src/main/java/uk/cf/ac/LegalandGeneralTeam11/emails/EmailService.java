package uk.cf.ac.LegalandGeneralTeam11.emails;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

    void sendSimpleMessage(
            String to, String subject, String text);

    void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException;


    public void sendSimpleMessage(String to, String subject, String templateName, Context context) ;}

