package com.kerimovikh.filter.connection;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebFilter("/*")
public class JdbcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();

        if (servletPath.contains("/specialPath1") || servletPath.contains("specialPath2")) {

            Connection connection = null;

            try {
                connection = ConnectionUtils.getConnection();
                connection.setAutoCommit(false);

                MyUtils.storeConnection(servletRequest, connection);

                filterChain.doFilter(servletRequest, servletResponse);

                connection.commit();
            } catch (Exception e) {
                ConnectionUtils.rollbackQuietly(connection);
                throw new RuntimeException(e);
            } finally {
                ConnectionUtils.closeQuietly(connection);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
