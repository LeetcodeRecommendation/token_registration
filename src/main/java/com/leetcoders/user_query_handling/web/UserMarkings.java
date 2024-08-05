package com.leetcoders.user_query_handling.web;

import com.leetcoders.user_query_handling.utils.CassandraHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMarkings {
    private final CassandraHandler cassandraHandler;
    public UserMarkings(CassandraHandler cassandraHandler) {
        this.cassandraHandler = cassandraHandler;
    }

    @PutMapping("/api/v1/update_leetcode_question")
    public ResponseEntity<Void> updateLeetCodeQuestion(@RequestBody @Valid LeetCodeQuestionMarking details,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (cassandraHandler.updateLeetCodeQuestion(details)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(500));
    }
    @PutMapping("/api/v1/youtube_marking")
    public ResponseEntity<Void> updateYoutubeMarking(@RequestBody @Valid YoutubeVideoMarking details,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (cassandraHandler.updateYoutubeVideo(details)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(500));
    }

}
