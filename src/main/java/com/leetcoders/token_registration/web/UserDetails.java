package com.leetcoders.token_registration.web;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.List;

public record UserDetails(
        @NonNull @NotEmpty String name,
        @NonNull @NotEmpty String token,
        @NonNull @NotEmpty List<String> companies) {
}
