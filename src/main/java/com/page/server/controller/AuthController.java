package com.page.server.controller;

import com.page.server.config.JwtTokenProvider;
import com.page.server.dto.SignDto;
import com.page.server.dto.UserDto;
import com.page.server.entity.User;
import com.page.server.service.MailService;
import com.page.server.service.UserSevice;
import com.sendgrid.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserSevice userSevice;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign/up")
    public Response createUser(@RequestBody SignDto.InRequest request) {

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.userName);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("This email is not available.");
        }

        Boolean isActivated = userSevice.getUserIsActivated(request.userName);
        if (isActivated == null) {
            userSevice.createUser(request);
        }

        if (isActivated != null && isActivated) {
            throw new IllegalArgumentException("You are already registered with this email!");
        }

        return mailService.sendMessage(
                request.userName,
                "Sign in notification from VUMY",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        );
    }

    @PostMapping("/sign/in")
    public ResponseEntity<SignDto.InResponse> getToken(@RequestBody SignDto.InRequest request) throws AuthenticationException {
        User user = userSevice.getUserByUserName(request.userName);
        if (user == null){
            throw new IllegalArgumentException("Can't find account.");
        }

        if (!user.isEnabled()) {
            throw new AuthenticationException("No visitors allowed.");
        }

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new AuthenticationException("Incorrect password.");
        }

        return ResponseEntity.ok(
                SignDto.InResponse.builder()
//                        .user(userSevice.convertUser(user))
                        .token(jwtTokenProvider.createToken(user.getUsername(), Collections.emptyList()))
                        .build()
        );
    }

    @GetMapping(value = "/secured/me")
    ResponseEntity<UserDto.Info> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                userSevice.convertUser(user)
        );
    }
}
