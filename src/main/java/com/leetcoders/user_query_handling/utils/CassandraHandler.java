package com.leetcoders.user_query_handling.utils;

import com.datastax.driver.core.*;
import com.leetcoders.user_query_handling.web.LeetCodeQuestionMarking;
import com.leetcoders.user_query_handling.web.YoutubeVideoMarking;
import org.springframework.lang.NonNull;

import java.util.List;

public class CassandraHandler {
    static final String KEYSPACE = "leetcode_rs";
    static final String UPDATE_LEET_CODE_QUESTION = """
            UPDATE user_questions SET manually_marked_by_user = ? WHERE user_name = ? AND question_name = ? IF EXISTS;
            """;
    static final String UPDATE_YOUTUBE_VIDEO = """
            UPDATE user_videos SET watched = ? WHERE user_name = ? AND video_name = ? IF EXISTS;
            """;
    final PreparedStatement updateLeetCodeQuestionStatement;
    final PreparedStatement updateYoutubeVideoStatement;
    final Cluster dbCluster;
    final Session dbSession;

    public CassandraHandler(@NonNull List<String> cassandraUrls) {
        Cluster.Builder connectionBuilder = Cluster.builder();
        cassandraUrls.forEach(connectionBuilder::addContactPoint);
        this.dbCluster = connectionBuilder.build();
        this.dbSession = this.dbCluster.connect(KEYSPACE);
        updateLeetCodeQuestionStatement = dbSession.prepare(UPDATE_LEET_CODE_QUESTION);
        updateYoutubeVideoStatement = dbSession.prepare(UPDATE_YOUTUBE_VIDEO);
    }

    public boolean updateLeetCodeQuestion(LeetCodeQuestionMarking details) {
        var res = this.dbSession.execute(updateLeetCodeQuestionStatement.bind(
                details.user_marked(),
                details.user_name(),
                details.question_name()));
        return !res.all().isEmpty();
    }

    public boolean updateYoutubeVideo(YoutubeVideoMarking details) {
        ResultSet res = this.dbSession.execute(updateYoutubeVideoStatement.bind(
                details.user_marked(),
                details.user_name(),
                details.video_name()));
        return !res.all().isEmpty();

    }
}
