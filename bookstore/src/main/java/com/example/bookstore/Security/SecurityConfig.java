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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//                        .requestMatchers("/api/users/**").authenticated()
//                        .requestMatchers("/api/books/**").authenticated()
//                        .requestMatchers("/api/loans/**").authenticated()
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form // Customize formLogin behavior
//                        // On successful login via API, return 200 OK
//                        .successHandler((request, response, authentication) -> {
//                            response.setStatus(HttpServletResponse.SC_OK); // HTTP 200 OK
//                            response.getWriter().write("Login successful"); // Optional: send a simple message in body
//                        })
//                        // On failed login via API, return 401 Unauthorized
//                        .failureHandler((request, response, exception) -> {
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 Unauthorized
//                            response.getWriter().write("Login failed: " + exception.getMessage()); // Send specific error message
//                        })
//                        .permitAll() // Ensure /login endpoint is accessible for everyone
//                )
//                .logout(logout -> logout // Configure logout behavior for API
//                        .logoutUrl("/logout") // Specify the logout URL
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.setStatus(HttpServletResponse.SC_OK); // HTTP 200 OK on successful logout
//                            response.getWriter().write("Logout successful");
//                        })
//                        .permitAll() // Ensure /logout endpoint is accessible
//                )
//                .exceptionHandling(exceptions -> exceptions
//                        // For unauthorized access to protected resources (e.g., trying to access /api/books without being logged in)
//                        // Return 401 Unauthorized instead of redirecting to login page
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for easier API testing (re-enable and handle for production web apps)
//
//        return http.build();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        // Ensure this origin matches your React app's development server URL
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("testuser")
//                .password(passwordEncoder.encode("StrongP@ssword123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}