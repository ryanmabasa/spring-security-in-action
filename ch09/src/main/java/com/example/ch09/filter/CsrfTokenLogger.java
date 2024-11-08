package com.example.ch09.filter;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.web.csrf.CsrfToken;


public class CsrfTokenLogger implements Filter {

    private final Logger logger = Logger.getLogger(CsrfTokenLogger.class.getName());



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CsrfToken o =
                (CsrfToken) servletRequest.getAttribute("_csrf");

        logger.info("CSRF token " + o.getToken());

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
