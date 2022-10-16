package com.example.bci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable Cross-Site Request Forgery (CSRF)
                .authorizeRequests( auth -> auth
                        // The user should be authenticated for any request in the application
                        .anyRequest().authenticated()
                )
                // Spring Security will never create an HttpSession and it will never use it to obtain the Security Context
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Spring Security’s HTTP Basic Authentication support is enabled by default. However, as soon as any servlet-based configuration is provided, HTTP Basic must be explicitly provided.
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("spring")
                        .password(passwordEncoder().encode("secret"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
