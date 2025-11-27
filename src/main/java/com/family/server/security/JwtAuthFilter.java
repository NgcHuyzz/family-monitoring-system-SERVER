package com.family.server.security;

import io.jsonwebtoken.JwtException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String ctx  = request.getContextPath();

        // Bỏ qua auth cho /api/auth/*
        if (path.startsWith(ctx + "/api/auth/")) {
            chain.doFilter(req, res);
            return;
        }

        // Chỉ bảo vệ /api/*
        if (!path.startsWith(ctx + "/api/")) {
            chain.doFilter(req, res);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String userId = JwtUtil.getUserId(token);
            request.setAttribute("userId", userId);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
