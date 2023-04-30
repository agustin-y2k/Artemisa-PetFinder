package com.PetFinder.Artemisa.auth;

import com.PetFinder.Artemisa.config.JwtService;
import com.PetFinder.Artemisa.email.EmailException;
import com.PetFinder.Artemisa.email.EmailService;
import com.PetFinder.Artemisa.exception.NotAuthorizedException;
import com.PetFinder.Artemisa.model.Role;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.token.Token;
import com.PetFinder.Artemisa.token.TokenRepository;
import com.PetFinder.Artemisa.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public String register(RegisterRequest request) throws EmailException {
        if ((repository.findByEmail(request.getEmail()).isPresent())){
            return "Email already exist";
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(false)
                .build();
        var jwtToken = jwtService.generateToken(user);

        repository.save(user);
        saveUserToken(user, jwtToken);

        emailService.sendMail(user.getEmail(), "Confirm your e-mail",
                "http://localhost:8080/api/v1/auth/confirm-account?token=" + jwtToken);

        return "Please confirm your e-mail, check spam folder";
    }

    public String confirmAccount(String token) {
        var userToken = tokenRepository.findByToken(token)
                .orElseThrow();
        if (userToken.isExpired())
            return "Token expired";
        if (userToken.isRevoked())
            return "Token revoked";
        var user = userToken.getUser();
        user.setEnabled(true);
        repository.save(user);
        userToken.setExpired(true);
        userToken.setRevoked(true);
        tokenRepository.save(userToken);
        return "Account confirmed";
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws NotAuthorizedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
