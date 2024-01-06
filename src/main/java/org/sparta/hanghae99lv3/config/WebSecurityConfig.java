package org.sparta.hanghae99lv3.config;

import lombok.RequiredArgsConstructor;
import org.sparta.hanghae99lv3.jwt.JwtAuthenticationFilter;
import org.sparta.hanghae99lv3.jwt.JwtAuthorizationFilter;
import org.sparta.hanghae99lv3.jwt.JwtUtil;
import org.sparta.hanghae99lv3.security.CustomAccessDeniedHandler;
import org.sparta.hanghae99lv3.security.StaffDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final StaffDetailsServiceImpl staffDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, staffDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests)->
                authorizeHttpRequests
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/join").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/teachers").hasAnyAuthority("AUTH_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/teachers/**").hasAnyAuthority("AUTH_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/teachers/**").hasAnyAuthority("AUTH_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/lectures").hasAnyAuthority("AUTH_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/lectures/**").hasAnyAuthority("AUTH_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/lectures/**").hasAnyAuthority("AUTH_ADMIN")
                        .anyRequest().authenticated()
        );

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
        );

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}