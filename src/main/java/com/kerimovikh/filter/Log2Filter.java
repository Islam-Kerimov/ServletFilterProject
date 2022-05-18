package com.kerimovikh.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class Log2Filter implements Filter {

    private String logFile;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logFile = filterConfig.getInitParameter("logFile");

        System.out.println("Log file " + logFile);
    }

    @Override
    public void destroy() {
        System.out.println("Log2Filter destroy!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (logFile != null) {
            logToFile(logFile);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void logToFile(String fileName) {
        System.out.println("Write log to file " + fileName);
    }
}
