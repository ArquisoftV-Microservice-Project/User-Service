package com.elgris.usersapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");
        final String path = request.getRequestURI();

        // Excluir endpoints públicos
        if (path.equals("/metrics") || path.equals("/health") ||
                path.equals("/info") || path.equals("/prometheus")) {
            chain.doFilter(req, res);
            return;
        }

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Path: " + path);
                throw new ServletException("Missing or invalid Authorization header" + " Path: " + path);
            }

            final String token = authHeader.substring(7);

            try {
                final Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);
        }
    }
}