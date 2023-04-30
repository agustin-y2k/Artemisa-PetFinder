package com.PetFinder.Artemisa.auth;

import com.PetFinder.Artemisa.config.JwtService;
import com.PetFinder.Artemisa.email.EmailException;
import com.PetFinder.Artemisa.email.EmailService;
import com.PetFinder.Artemisa.exception.NotAuthorizedException;
import com.PetFinder.Artemisa.model.User;
import com.PetFinder.Artemisa.repository.UserRepository;
import com.PetFinder.Artemisa.token.Token;
import com.PetFinder.Artemisa.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() throws EmailException {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setAddress("123 Main St.");
        request.setPhone("555-555-5555");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        String result = authenticationService.register(request);

        assertEquals("Please confirm your e-mail, check spam folder", result);

        verify(repository, times(1)).save(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(emailService, times(1)).sendMail(eq(request.getEmail()), eq("Confirm your e-mail"),
                eq("http://localhost:8080/api/v1/auth/confirm-account?token=jwtToken"));
    }

    @Test
    public void testConfirmAccount() {
        Token token = new Token();
        token.setExpired(false);
        token.setRevoked(false);
        token.setUser(new User());

        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        String result = authenticationService.confirmAccount("jwtToken");

        assertEquals("Account confirmed", result);
        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
        verify(repository, times(1)).save(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    public void testAuthenticate() throws NotAuthorizedException {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("johndoe@example.com");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("jwtToken", response.getToken());
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}