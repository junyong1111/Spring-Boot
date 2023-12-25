package com.springbootwithawsjyp.springbootwithaws.config;//package com.springbootwithawsjyp.springbootwithaws.basic;

import com.springbootwithawsjyp.springbootwithaws.config.auth.CustomOAuth2UserService;
import com.springbootwithawsjyp.springbootwithaws.domain.users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuthSecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain OAuth2filterChain(HttpSecurity http) throws Exception {
         http.csrf(AbstractHttpConfigurer::disable);
         http.authorizeHttpRequests(
                 auth -> auth.requestMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                         .requestMatchers("/api/vi/**").hasRole(Role.USER.name())
                         .anyRequest().authenticated());
         http.logout(
                 logout -> logout.logoutSuccessUrl("/")
                 );
         http.oauth2Login(oauth2Login ->
                oauth2Login.userInfoEndpoint()
                        .userService(customOAuth2UserService) // 여기에 CustomOAuth2UserService 설정
        );
        return http.build();
    }
}
