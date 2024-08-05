package com.leetcoders.token_registration.utils;
import io.github.cdimascio.dotenv.Dotenv;
public class EnvironmentHandler {
    public static String getPGServerIP() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("PG_SERVER_IP");
    }
    public static Integer getPGServerPort() {
        Dotenv dotenv = Dotenv.load();
        return Integer.valueOf(dotenv.get("PG_SERVER_PORT"));
    }
    public static String getPGUsername() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("PG_SERVER_USERNAME");
    }

    public static String getPGPassword() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("PG_SERVER_PASSWORD");
    }
}
