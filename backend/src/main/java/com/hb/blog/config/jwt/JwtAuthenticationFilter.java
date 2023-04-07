package com.hb.blog.config.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.blog.config.auth.PrincipalDetailsService;
import com.hb.blog.dto.ErrorCode;
import com.hb.blog.dto.ResponseDto;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            String path = request.getRequestURI();
            if (path.equals("/api/user/refresh") || path.equals("/api/user/login")) {
                filterChain.doFilter(request, response);
                return;
            }
            String userName = jwtTokenProvider.getUserNameByToken(token);
            UserDetails user = principalDetailsService.loadUserByUsername(userName);

            if (jwtTokenProvider.isTokenValid(token, user)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            ResponseDto<Object> responseDto = new ResponseDto<>(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getStatus().value(),
                    ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getMessage());
            response.setStatus(responseDto.getStatus());
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
            response.getWriter().flush();
        }
    }

}
