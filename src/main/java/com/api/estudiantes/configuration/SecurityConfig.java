package com.api.estudiantes.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpMethod.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.estudiantes.configuration.filter.JwtTokenValidator;
import com.api.estudiantes.service.CustomUserDetailsService;
import com.api.estudiantes.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtUtils jwtUtils;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
      AuthenticationProvider authenticationProvider) throws Exception {
    return httpSecurity.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(http -> {
          // Public EndPoints
          http.requestMatchers(POST, "/auth/**").permitAll();
          http.requestMatchers("/docs/**").permitAll();
          // Company EndPoints
          http.requestMatchers(GET, "/company").permitAll();
          http.requestMatchers(POST, "/company").hasRole("ADMIN");
          http.requestMatchers(PUT, "/company/**").hasRole("ADMIN");
          // User EndPoints
          http.requestMatchers(POST, "/users/signup").permitAll();
          http.requestMatchers(POST, "/users").hasRole("ADMIN");
          http.requestMatchers(GET, "/users").hasRole("ADMIN");
          http.requestMatchers(GET, "/users/**").hasRole("ADMIN");
          http.requestMatchers(PUT, "/users/**").hasRole("ADMIN");
          http.requestMatchers(DELETE, "/users/**").hasRole("ADMIN");
          
          // Estudiantes EndPoints
          http.requestMatchers(GET, "/estudiantes/**").hasAnyRole("ADMIN", "ESTUDIANTE");
          http.requestMatchers(POST, "/estudiantes").hasRole("ADMIN");
          http.requestMatchers(PUT, "/estudiantes/**").hasRole("ADMIN");
          http.requestMatchers(DELETE, "/estudiantes/**").hasRole("ADMIN");
          
          // Materias EndPoints
          http.requestMatchers(GET, "/materias/**").hasAnyRole("ADMIN", "ESTUDIANTE");
          http.requestMatchers(POST, "/materias").hasRole("ADMIN");
          http.requestMatchers(PUT, "/materias/**").hasRole("ADMIN");
          http.requestMatchers(DELETE, "/materias/**").hasRole("ADMIN");
          
          // Notas EndPoints
          http.requestMatchers(GET, "/notas/**").hasAnyRole("ADMIN", "ESTUDIANTE");
          http.requestMatchers(POST, "/notas").hasRole("ADMIN");
          http.requestMatchers(PUT, "/notas/**").hasRole("ADMIN");
          http.requestMatchers(DELETE, "/notas/**").hasRole("ADMIN");
          
          // Financiera EndPoints
          http.requestMatchers(GET, "/financiera/**").hasAnyRole("ADMIN", "ESTUDIANTE");
          http.requestMatchers(POST, "/financiera").hasRole("ADMIN");
          http.requestMatchers(PUT, "/financiera/**").hasRole("ADMIN");
          http.requestMatchers(DELETE, "/financiera/**").hasRole("ADMIN");
      
          http.anyRequest().denyAll();
        }).addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration
        .setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
