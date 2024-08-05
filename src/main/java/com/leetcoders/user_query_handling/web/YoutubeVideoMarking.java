package com.leetcoders.user_query_handling.web;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

public record YoutubeVideoMarking(
        @NonNull @NotEmpty String user_name,
        @NonNull @NotEmpty String video_name,
        @NonNull Boolean user_marked

) {
}
