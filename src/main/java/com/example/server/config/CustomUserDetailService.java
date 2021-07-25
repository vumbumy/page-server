package com.example.server.config;

import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("USER NOT FOUND :" + userName);
        }

        return optionalUser.get();
    };
}
