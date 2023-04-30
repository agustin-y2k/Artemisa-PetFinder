package com.PetFinder.Artemisa.auth;

import com.PetFinder.Artemisa.email.EmailException;
import com.PetFinder.Artemisa.exception.NotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authService;

    private AuthenticationController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthenticationController(authService);
    }

    @Test
    void testRegister() throws EmailException {
        RegisterRequest request = new RegisterRequest();
        String expectedResult = "success";

        // Simulate AuthenticationService.register() behavior
        when(authService.register(request)).thenReturn(expectedResult);

        String result = authController.register(request);

        assertEquals(expectedResult, result);
    }

    @Test
    void testConfirmAccount() {
        String token = "12345";
        String expectedResult = "success";

        // Simulate AuthenticationService.confirmAccount() behavior
        when(authService.confirmAccount(token)).thenReturn(expectedResult);

        ResponseEntity<String> result = authController.confirmAccount(token);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResult, result.getBody());
    }

    @Test
    void testAuthenticate() throws NotAuthorizedException {
        AuthenticationRequest request = new AuthenticationRequest();
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token");

        // Simulate AuthenticationService.authenticate() behavior
        when(authService.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> result = authController.authenticate(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }
}