package com.hms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
// @Configuration files has to be load first in the project. Before any classes.
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http  // all the HTTP request(GET, POST etc.) will be captured by http.
    ) throws Exception{


        http.csrf().disable().cors().disable();
// Cross Site Request Forgery Protection--> In Spring Security,It is a security feature enabled by
// default to prevent unauthorized commands from being transmitted from a user that the web application
// trusts.

//      this line of code will help to run jwtFilter method to run before and built in filter method.
//      so  url's should be access with the JWT authentication.
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
