package com.leetcoders.user_query_handling.web;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.List;

public record UserDetails(
        @NonNull @NotEmpty String name,
        @NonNull @NotEmpty String token,
        @NonNull @NotEmpty String csrfToken,
        @NonNull @NotEmpty String expirationTime,
        @NonNull @NotEmpty List<String> companies) {
}
