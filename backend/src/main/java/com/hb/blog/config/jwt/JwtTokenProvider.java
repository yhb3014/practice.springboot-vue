package com.hb.blog.config.jwt;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.blog.config.auth.PrincipalDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private static final String AUTHORITIES_KEY = "auth";

    private PrincipalDetailsService principalDetailsService;

    public JwtTokenProvider(@Value("${jwt.key}") String secretKey,
            @Value("${jwt.accessTokenExpiration}") String accessTokenExpiration,
            @Value("${jwt.refreshTokenExpiration}") String refreshTokenExpiration,
            PrincipalDetailsService principalDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = Duration.ofMinutes(Long.parseLong(accessTokenExpiration)).toMillis();
        this.refreshTokenExpiration = Duration.ofDays(Long.parseLong(refreshTokenExpiration)).toMillis();
        this.principalDetailsService = principalDetailsService;
    }

    /**
     * accessToken, refreshToken 생성
     * 
     * @param authentication
     * @return
     */
    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpiration);

        String accessToken = newAccessToken(authentication, authorities, accessTokenExpiresIn);

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType(JwtProperties.TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return tokenInfo;
    }

    /**
     * accessToken 생성
     * 
     * @param authentication
     * @param authorities
     * @param accessTokenExpiresIn
     * @return
     */
    public String newAccessToken(Authentication authentication, String authorities, Date accessTokenExpiresIn) {
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    /**
     * refreshToken 만료 검증 후, accessToken 재생성
     * 
     * @param tokenInfo
     * @param authentication
     * @return
     */
    public String validateRefreshToken(String refreshToken, Authentication authentication) {
        String accessToken = null;
        try {
            long now = (new Date()).getTime();
            Date accessTokenExpiresIn = new Date(now + accessTokenExpiration);
            if (!isTokenExpired(refreshToken)) {
                accessToken = newAccessToken(authentication, refreshToken, accessTokenExpiresIn);

                return accessToken;
            }
        } catch (Exception e) {
            //
        }

        return accessToken;
    }

    /**
     * 토큰으로 정보 추출
     * 
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = paresClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보 없음");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails user = principalDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(user, authorities);
    }

    /**
     * 토큰 추출
     * 
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.HEADER_STRING);
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * 만료된 토큰 payload 값 추출
     * 
     * @param token
     * @return
     */
    public HashMap<String, String> getPayloadByToken(String token) {
        try {
            String[] splitJwt = token.split("\\.");

            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(splitJwt[1].getBytes()));

            return new ObjectMapper().readValue(payload, HashMap.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (getUserNameByToken(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return paresClaims(token).getExpiration().before(new Date());
    }

    public String getUserNameByToken(String token) {
        return paresClaims(token).getSubject();
    }

    public Claims paresClaims(String accessToken) {
        Claims parseClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return parseClaims;
    }
}
