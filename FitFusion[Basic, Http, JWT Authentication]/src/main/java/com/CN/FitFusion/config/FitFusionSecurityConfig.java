package com.CN.FitFusion.config;

import com.CN.FitFusion.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * Security configuration class for the FitFusion application.
 * Configures security settings, including HTTP security, authentication, and password encoding.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FitFusionSecurityConfig {

    @Autowired
    JwtAuthenticationFilter filter;

    /**
     * Configures HTTP security for the application.
     * <p>
     * This method disables CSRF protection, configures URL access rules, and sets session management to stateless.
     * It ensures that all requests, except for registration and login, require authentication.
     * Additionally, it adds a custom JWT authentication filter to process requests before the default authentication filter.
     * </p>
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/user/register", "/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * <p>
     * This method creates an {@link AuthenticationManager} instance used by Spring Security to handle authentication requests.
     * It is essential for managing and validating user credentials.
     * </p>
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    /**
     * Provides a {@link PasswordEncoder} bean for encoding passwords.
     * <p>
     * This method returns a {@link BCryptPasswordEncoder} instance, which is used to hash passwords securely before storing them.
     * Password encoding is crucial for protecting user credentials from being exposed in plaintext.
     * </p>
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
