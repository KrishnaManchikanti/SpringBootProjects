package com.Assignment.LibraryManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LibrarySecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/user/register","/author/register").permitAll()
                .antMatchers("/user/delete").hasAnyRole("USER","ADMIN")
                .antMatchers("/loan/all-borrowed","/loan/overdue","/loan/all").hasRole("ADMIN")
                .antMatchers("/loan/amount-to-be-paid").hasAnyRole("ADMIN", "USER")
                .antMatchers("/book/all","/book/all-available").hasRole("ADMIN")
                .antMatchers("/author/update","/author/delete").hasRole("AUTHOR")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService users()
    {
        UserDetails user1 = User.builder()
                .username("author")
                .password(passwordEncoder().encode("author"))
                .roles("AUTHOR")
                .build();

        UserDetails user2 = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails user3 = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
