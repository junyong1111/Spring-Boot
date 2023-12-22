//package com.springbootwithawsjyp.springbootwithaws.basic;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//
//@Configuration
//public class BasicAuthSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(
//                auth -> auth.anyRequest().authenticated()
//        );
//        http.httpBasic(Customizer.withDefaults());
//        http.sessionManagement(
//                session -> session.sessionCreationPolicy
//                        (SessionCreationPolicy.STATELESS));
//        http.csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
//}
