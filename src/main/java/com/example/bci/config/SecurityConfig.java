package com.example.bci.config;

import com.example.bci.security.JwtAuthenticationEntryPoint;
import com.example.bci.security.JwtAuthenticationFilter;
import com.example.bci.security.JwtProperties;
import com.example.bci.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints
            "/h2-console/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProperties jwtProperties) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable Cross-Site Request Forgery (CSRF)
                .authorizeRequests( auth -> auth
                        .antMatchers(HttpMethod.POST,"/signup").anonymous()
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        // The user should be authenticated for any request in the application
                        .anyRequest().authenticated()
                )
                // Spring Security will never create an HttpSession, and it will never use it to obtain the Security Context
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))

                .addFilterBefore(new JwtAuthenticationFilter(users(), jwtUtils(jwtProperties)), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("eatatkyles@acme.com")
                        .password(passwordEncoder().encode("1aA!4567"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtils jwtUtils(JwtProperties jwtProperties) {
        return new JwtUtils(jwtProperties);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
