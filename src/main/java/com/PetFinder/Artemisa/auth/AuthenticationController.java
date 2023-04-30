package com.PetFinder.Artemisa.auth;

import com.PetFinder.Artemisa.email.EmailException;
import com.PetFinder.Artemisa.exception.NotAuthorizedException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "Artemisa PetFinder API", version = "1.0", description = "Artemisa PetFinder API"))
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    // http://localhost:8080/api/v1/auth/register
    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) throws EmailException {
        return service.register(request);
    }

    // http://localhost:8080/api/v1/auth/confirm-account
    @Operation(summary = "Confirm account", description = "Confirm account")
    @GetMapping("/confirm-account")
    public ResponseEntity<String> confirmAccount(
            @RequestParam("token") String token
    ) {
        return ResponseEntity.ok(service.confirmAccount(token));
    }

    // http://localhost:8080/api/v1/auth/authenticate
    @Operation(summary = "Authenticate", description = "Authenticate")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws NotAuthorizedException {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
