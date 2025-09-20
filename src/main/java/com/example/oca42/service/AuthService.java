package com.example.oca42.service;

import com.example.oca42.model.AuthRequest;
import com.example.oca42.model.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //AuthenticationManager
    //ProviderManager
    //AuthenticationProvider

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = (User) authenticate.getPrincipal();

        String token = jwtService.generateToken(user);

        return AuthResponse.builder().accessToken(token).build();
    }
}
