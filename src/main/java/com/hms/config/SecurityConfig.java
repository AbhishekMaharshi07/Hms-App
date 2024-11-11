package com.hms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{


        http.csrf().disable().cors().disable();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

//      Here we permit all url to access without authentication.
        http.authorizeHttpRequests().anyRequest().permitAll();

//      But here we can access few url without authentication.
//                http.authorizeHttpRequests().
//                requestMatchers("/api/v1/users/login", "/api/v1/users/signup-user",
//                         "/api/v1/users/signup-property-owner")
//                .permitAll()
//                .requestMatchers("api/v1/country/addCountry").hasRole("OWNER")
//                .anyRequest().authenticated();
// hasRole & hasAnyRole -->
        return http.build();
    }
}
