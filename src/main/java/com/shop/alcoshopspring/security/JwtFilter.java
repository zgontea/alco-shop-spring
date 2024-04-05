package com.shop.alcoshopspring.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenProvider tokenProvider;
    private static final int tokenIndex = 7;
    private static final Set<String> filterUrls = new HashSet<>(Arrays.asList(
            "/api/products/del/*",
            "/api/products/add",
            "/api/products/upd",
            "/api/users/del/*",
            "/api/users/all",
            "/api/users/id",
            "/api/users/email",
            "/api/users/upd",
            "/api/orders/sendOrder",
            "/api/orders/userOrder",
            "/api/orderDetails/save",
            "/api/orderDetails/del",
            "/api/orders/id"));
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = ((HttpServletRequest) request).getHeader("authorization");
        String token;
        String username = null;

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header!\n");
        } else {
            token = header.substring(tokenIndex);
            try {
                username = tokenProvider.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.err.println("An error occured during getting username from token");
            } catch (ExpiredJwtException e) {
                System.err.println("The token is expired and not valid anymore");
                response.sendError(440, "Login Time-out");
            } catch (SignatureException e) {
                System.err.println("Authentication Failed. Username or Password not valid");
            } catch (MalformedJwtException e) {
                System.err.println(e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (tokenProvider.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(token,
                        SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                System.out.println("Authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return filterUrls.stream().noneMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

}
