package com.leetcoders.user_query_handling.utils;

import com.leetcoders.user_query_handling.web.UserDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Timestamp;


public class PostgresHandler {
    private static final String UPDATE_USER_DETAILS = """
            INSERT INTO user_details (name, access_key, csrf_token, companies, solved_questions, time_to_update, access_key_expiration, being_processed)
            VALUES
                (
                    ?,
                    ?,
                    ?,
                    ?,
                    0,
                    now() AT TIME ZONE 'UTC',
                    ?,
                    FALSE
                )
            ON CONFLICT (name)
            DO
            UPDATE
            SET name=?, access_key=?, csrf_token=?, companies=?, time_to_update=now() AT TIME ZONE 'UTC', access_key_expiration=?, being_processed=FALSE;
            """;
    private static final Logger logger = LoggerFactory.getLogger(PostgresHandler.class);
    private static final String DB_NAME = "leetcode-rs";
    private static HikariDataSource ds;

    public PostgresHandler(String serverName, int serverPort, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql://%s:%d/%s", serverName, serverPort, DB_NAME));
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("connectionTimeout", "30000");
        ds = new HikariDataSource(config);
        logger.info("Opened postgres connection pool successfully");
    }

    public boolean updateUserDetails(UserDetails userDetails) {
        LocalDate localDate = LocalDate.parse(userDetails.expirationTime());
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        Timestamp timestamp = Timestamp.from(instant);

        try (Connection connection = ds.getConnection(); var statement = connection.prepareStatement(UPDATE_USER_DETAILS)) {
            statement.setString(1, userDetails.name());
            statement.setString(6, userDetails.name());
            statement.setString(2, userDetails.token());
            statement.setString(7, userDetails.token());
            statement.setString(3, userDetails.csrfToken());
            statement.setString(8, userDetails.csrfToken());
            java.sql.Array companiesArray = connection.createArrayOf("text", userDetails.companies().toArray());
            statement.setArray(4, companiesArray);
            statement.setArray(9, companiesArray);
            statement.setTimestamp(5, timestamp);
            statement.setTimestamp(10, timestamp);

            statement.execute();
            logger.info("User successfully updated");
            return true;
        } catch (SQLException e) {
            logger.error("Failed updating user", e);
            return false;
        }
    }
}
