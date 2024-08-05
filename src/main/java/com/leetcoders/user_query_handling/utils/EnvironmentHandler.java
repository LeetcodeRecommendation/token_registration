package com.leetcoders.user_query_handling.utils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Arrays;
import java.util.List;

public class EnvironmentHandler {
    public static String getPGServerIP() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        return dotenv.get("PG_SERVER_IP");
    }
    public static Integer getPGServerPort() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        return Integer.valueOf(dotenv.get("PG_SERVER_PORT"));
    }
    public static String getPGUsername() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        return dotenv.get("PG_SERVER_USERNAME");
    }

    public static String getPGPassword() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        return dotenv.get("PG_SERVER_PASSWORD");
    }

    public static List<String> getCassandraUrls() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String urls = dotenv.get("CASSANDRA_URL");
        return Arrays.stream(urls.split(";")).toList();
    }
}
