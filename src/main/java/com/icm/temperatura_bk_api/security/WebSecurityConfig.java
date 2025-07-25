package com.icm.temperatura_bk_api.security;

import com.icm.temperatura_bk_api.config.WebConfig;
import com.icm.temperatura_bk_api.security.filtrers.JwtAuthenticationFilter;
import com.icm.temperatura_bk_api.security.filtrers.JwtAuthorizationFilter;
import com.icm.temperatura_bk_api.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(WebConfig.class)
public class WebSecurityConfig {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SecurityUserDetailsServiceImpl securityUserDetailsService;

    @Autowired
    private JwtAuthorizationFilter authorizationFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://telemetriaperu.com:3010", "http://localhost:3000", "http://192.168.1.232:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        HeaderWriter headerWriter = new StaticHeadersWriter("Access-Control-Allow-Origin", "http://telemetriaperu.com:3010", "http://localhost:3000", "http://192.168.1.232:3000");

        return httpSecurity
                .cors()
                .and()
                .csrf(config -> config.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .headers(headers -> headers.addHeaderWriter(headerWriter))
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/api/v1/companies/**").permitAll();
                    auth.requestMatchers("/api/v1/observations/**").permitAll();
                    auth.requestMatchers("/api/v1/platforms/**").permitAll();
                    auth.requestMatchers("/api/v1/temperature-logs/**").permitAll();
                    auth.requestMatchers("/api/fuel-efficiency/download-excel/**").permitAll();
                    // .hasRole("ADMINISTRATOR");
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/doc/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-resources/**").permitAll();
                    auth.requestMatchers("/webjars/**").permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }
}