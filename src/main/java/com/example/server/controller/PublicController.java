package com.example.server.controller;

import com.example.server.config.JwtTokenProvider;
import com.example.server.dto.Sign;
import com.example.server.dto.User;
import com.example.server.entity.UserEntity;
import com.example.server.service.UserSevice;
import com.sendgrid.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class PublicController {
    private final UserSevice userSevice;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${sendgrid.api-key}")
    private String SENDGRID_API_KEY;

    @Value("${sendgrid.from}")
    private String SENDGRID_FROM;

    @PostMapping("/sign/up")
    public ResponseEntity<User.Info> createUser(@RequestBody Sign.InRequest request) {

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.userName);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("This email is not available.");
        }

        Email from = new Email(SENDGRID_FROM);
        String subject = "Sign in notification from VUMY";
        Email to = new Email(request.userName);
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);

        Request sgRequest = new Request();
        try {
            sgRequest.setMethod(Method.POST);
            sgRequest.setEndpoint("mail/send");
            sgRequest.setBody(mail.build());

            sg.api(sgRequest);
        } catch (IOException ex) {
            ex.printStackTrace();

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(
                userSevice.createUser(request)
        );
    }

    @PostMapping("/sign/in")
    public ResponseEntity<Sign.InResponse> getToken(@RequestBody Sign.InRequest request) throws AuthenticationException {
        UserEntity user = userSevice.getUserByUserName(request.userName);
        if (!user.isEnabled()) {
            throw new AuthenticationException("No visitors allowed.");
        }

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new AuthenticationException("Incorrect password.");
        }

        return ResponseEntity.ok(
                Sign.InResponse.builder()
//                        .user(userSevice.convertUser(user))
                        .token(jwtTokenProvider.createToken(user.getUsername(), Collections.emptyList()))
                        .build()
        );
    }
}
