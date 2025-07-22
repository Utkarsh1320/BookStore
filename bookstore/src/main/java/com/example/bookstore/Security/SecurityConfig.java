package com.example.bookstore.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // <-- Import CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // <--- ENABLE CORS here, it will find the bean below
                .authorizeHttpRequests(authorize -> authorize
                        // Allow POST requests to /api/users (for user creation/registration)
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // Other /api/users methods (GET, PUT, DELETE) require authentication
                        .requestMatchers("/api/users/**").authenticated() // Protects /api/users, /api/users/{id} etc.
                        // Protect /api/books and /api/loans (they will also require authentication)
                        .requestMatchers("/api/books/**").authenticated()
                        .requestMatchers("/api/loans/**").authenticated()
                        // Allow Swagger UI and API docs to be publicly accessible
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // Use default form login
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for easier API testing (consider enabling for production web apps)

        return http.build();
    }

    @Bean
    // CHANGE: The return type must be CorsConfigurationSource, not CorsConfiguration
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        // It's common for React dev server to be on http, not https, by default.
        // Double-check your React app's actual URL when it starts (e.g., http://localhost:3000)
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // Changed to http
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, OPTIONS)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this CORS config to all paths
        return source; // This correctly returns a UrlBasedCorsConfigurationSource which implements CorsConfigurationSource
    }

    @Bean
    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("testuser")
                .password(passwordEncoder.encode("StrongP@ssword123"))
                .roles("USER") // Role for the in-memory test user
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}