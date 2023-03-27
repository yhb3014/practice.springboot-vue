package com.hb.blog.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hb.blog.config.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {

                return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .formLogin().disable()
                                .httpBasic().disable()
                                .csrf().disable()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http
                                .authorizeHttpRequests()
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated();

                http
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
