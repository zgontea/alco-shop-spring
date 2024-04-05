package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.security.TokenProvider;
import com.shop.alcoshopspring.models.User;
import com.shop.alcoshopspring.services.UserManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
	private BCryptPasswordEncoder bcryptEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
                        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);

        User userFromDatabase = userManager.findByEmail(loginRequest.getLogin());

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        userFromDatabase.getId(),
                        userFromDatabase.getEmail(),
                        userFromDatabase.getFirstname(),
                        userFromDatabase.getLastname(),
                        userFromDatabase.isAdmin()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        User existingUser = userManager.findByEmail(registerRequest.email);
        if (existingUser != null) {
            return ResponseEntity.status(409).build();
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(bcryptEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .phone(registerRequest.getPhone())
                .admin(false)
                .build();

        userManager.save(user);

        return ResponseEntity.ok().build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class LoginRequest {
        String login;
        String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class LoginResponse {
        String access_token;
        Long user_id;
        String email;
        String firstname;
        String lastname;
        Boolean isAdmin;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RegisterRequest {
        String email;
        String password;
        String firstname;
        String lastname;
        String phone;
    }
}
