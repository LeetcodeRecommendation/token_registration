package com.leetcoders.token_registration.utils;

import com.leetcoders.token_registration.web.UserDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class PostgresHandler {
    private static final String CREATE_DB_STATEMENT = """
            DO $$
            BEGIN
                IF NOT EXISTS (SELECT FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'user_details')
                THEN
                    CREATE TABLE user_details (
                        name varchar(255) PRIMARY KEY,
                        access_key varchar(255),
                        companies text ARRAY,
                        solved_questions int,
                        time_to_update timestamp
                    );
                END IF;
            END $$;
            """;
    private static final String UPDATE_USER_DETAILS = """
        INSERT INTO user_details (name, access_key, companies, solved_questions, time_to_update)
        VALUES
            (
                ?,
                ?,
                ?,
                0,
                now() AT TIME ZONE 'UTC'
            )
        ON CONFLICT (name)
        DO
        UPDATE
        SET name=?, access_key=?, companies=?, time_to_update=now() AT TIME ZONE 'UTC';
        """;
    private static final Logger logger = LoggerFactory.getLogger(PostgresHandler.class);
    private static final String DB_NAME = "leetcode-rs";
    private static HikariDataSource ds;

    public PostgresHandler(String serverName, int serverPort, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql://%s:%d/%s",serverName, serverPort, DB_NAME));
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("connectionTimeout", "30000");
        ds = new HikariDataSource(config);
        logger.info("Opened postgres connection pool successfully");
        initializeUserTable();
    }

    protected void initializeUserTable() {
        try (Connection connection = ds.getConnection()) {
            connection.createStatement().execute(CREATE_DB_STATEMENT);
            logger.info("PostgresDB successfully initialized");
        } catch (SQLException e) {
            logger.error("Failed initializing table due to exception", e);
            System.exit(-1);
        }
    }

    public boolean updateUserDetails(UserDetails userDetails) {
        try (Connection connection = ds.getConnection()) {
            var statement = connection.prepareStatement(UPDATE_USER_DETAILS);
            statement.setString(1, userDetails.name());
            statement.setString(4, userDetails.name());
            statement.setString(2, userDetails.token());
            statement.setString(5, userDetails.token());
            java.sql.Array companiesArray = connection.createArrayOf("text", userDetails.companies().toArray());
            statement.setArray(3, companiesArray);
            statement.setArray(6, companiesArray);

            statement.execute();
            logger.info("User successfully updated");
            return true;
        } catch (SQLException e) {
            logger.error("Failed updating user", e);
            return false;
        }
    }
}
