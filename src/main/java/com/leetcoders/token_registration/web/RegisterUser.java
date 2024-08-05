package com.leetcoders.token_registration.web;

import com.leetcoders.token_registration.utils.PostgresHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterUser {
    protected PostgresHandler postgresHandler;

    public RegisterUser(PostgresHandler postgresHandler) {
        this.postgresHandler = postgresHandler;
    }

    @PutMapping("/api/v1/update_user_token")
    public ResponseEntity<Void> registerUserWithToken(@RequestBody @Valid UserDetails details,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors, e.g., return a response with error details
            return ResponseEntity.badRequest().body(null); // Or handle differently based on your needs
        }
        if (postgresHandler.updateUserDetails(details)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(500));
    }
}
