package com.nahuannghia.shopnhn.config.security_config;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final String TOKEN_PREFIX = "Bearer ";
    private final String HEADER_STRING = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            logger.debug("No JWT token found in request headers");
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TOKEN_PREFIX, "");
        String username = null;
        Claims claims = null;

//        try {
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY.getBytes())
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            username = claims.getSubject();
//        } catch (JwtException e) {
//            logger.warn("JWT Token parsing failed: {}", e.getMessage());
//            chain.doFilter(request, response);
//            return;
//        }
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            username = claims.getSubject();
        } catch (JwtException e) {
            logger.error("JWT Token parsing failed for token: {}. Error: {}", token, e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
        }

//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (isTokenValid(claims, userDetails)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                logger.debug("Authenticated user: {}", username);
//            }
//        }

        chain.doFilter(request, response);
    }

    private boolean isTokenValid(Claims claims, UserDetails userDetails) {
        String username = claims.getSubject();
        boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(claims);
        if (!isValid) {
            logger.warn("Invalid JWT token for user: {}", username);
        }
        return isValid;
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration() != null && claims.getExpiration().before(new Date());
    }
}