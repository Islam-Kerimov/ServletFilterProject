package com.kerimovikh.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.png", "*.jpg", "*.gif"}, initParams = {
        @WebInitParam(name = "notFoundImage", value = "/images/noImage.png")})
public class ImageFilter implements Filter {

    private String notFoundImage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notFoundImage = filterConfig.getInitParameter("notFoundImage");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();

        String realRootPath = servletRequest.getServletContext().getRealPath("");

        String imageRealPath = realRootPath + servletPath;

        System.out.println("imageRealPath = " + imageRealPath);

        File file = new File(imageRealPath);

        if (file.exists()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (!servletPath.equals(notFoundImage)) {
//            ((HttpServletResponse)servletResponse).sendRedirect(((HttpServletRequest)servletRequest).getContextPath() + notFoundImage);
            servletRequest.getServletContext().getRequestDispatcher(notFoundImage).forward(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
