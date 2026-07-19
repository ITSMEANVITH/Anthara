package com.eventbooking.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        
        String origin = req.getHeader("Origin");

        // Always allow local dev. Allow additional origins via the
        // ALLOWED_ORIGINS env var (comma-separated), e.g.
        // "https://anthara.pages.dev,https://anthara.mydomain.com"
        java.util.Set<String> allowedOrigins = new java.util.HashSet<>();
        allowedOrigins.add("http://localhost:8081");
        String extra = System.getenv("ALLOWED_ORIGINS");
        if (extra != null && !extra.isBlank()) {
            for (String o : extra.split(",")) {
                allowedOrigins.add(o.trim());
            }
        }

        if (origin != null && allowedOrigins.contains(origin)) {
            res.setHeader("Access-Control-Allow-Origin", origin);
        }

        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}