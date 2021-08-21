package com.page.server.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailService {
    @Value("${sendgrid.api-key}")
    private String SENDGRID_API_KEY;

    @Value("${sendgrid.from}")
    private String SENDGRID_FROM;

    public Response sendMessage(String email, String subject, String message) {
        Email from = new Email(SENDGRID_FROM);
//        String subject = "Sign in notification from VUMY";
        Email to = new Email(email);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);

        Request sgRequest = new Request();
        try {
            sgRequest.setMethod(Method.POST);
            sgRequest.setEndpoint("mail/send");
            sgRequest.setBody(mail.build());

            return sg.api(sgRequest);
        } catch (IOException ex) {
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ex.getMessage(),
                    null
            );
        }
    }
}
