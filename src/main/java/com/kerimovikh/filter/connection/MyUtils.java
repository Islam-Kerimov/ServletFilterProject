package com.kerimovikh.filter.connection;

import jakarta.servlet.ServletRequest;

import java.sql.Connection;

public class MyUtils {

    public static final String ATT_NAME = "MY_CONNECTION_ATTRIBUTE";

    public static void storeConnection(ServletRequest request, Connection connection) {
        request.setAttribute(ATT_NAME, connection);
    }

    public static Connection getStoredConnection(ServletRequest request) {
        return (Connection) request.getAttribute(ATT_NAME);
    }
}
