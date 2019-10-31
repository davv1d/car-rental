package com.davv1d.security;

import com.davv1d.security.user.UserPrincipalDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.davv1d.security.JwtProperties.STRING_HEADER;
import static com.davv1d.security.JwtProperties.TOKEN_TYPE;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getToken(request);
            if (jwtToken != null && tokenProvider.validateJwtToken(jwtToken)) {
                String userNameFromJwtToken = tokenProvider.getUserNameFromJwtToken(jwtToken);
                UserDetails userDetails = userPrincipalDetailService.loadUserByUsername(userNameFromJwtToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOGGER.error("Can not set user authentication -> Message: ", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(STRING_HEADER);
        if (authHeader != null && authHeader.startsWith(TOKEN_TYPE)) {
            return authHeader.replace(TOKEN_TYPE, "");
        }
        return null;
    }
}
