package com.hb.blog.config.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hb.blog.config.auth.PrincipalDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expiration;
    private static final String AUTHORITIES_KEY = "auth";

    private PrincipalDetailsService principalDetailsService;

    public JwtTokenProvider(@Value("${jwt.key}") String secretKey,
            @Value("${jwt.expiration}") String expiration,
            PrincipalDetailsService principalDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = Long.parseLong(expiration);
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
        Date accessTokenExpiresIn = new Date(now + expiration);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + expiration))
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
     * 토큰 정보 추출
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
        Claims parseClaims = null;
        try {
            parseClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            parseClaims = e.getClaims();
        }

        return parseClaims;
    }
}
