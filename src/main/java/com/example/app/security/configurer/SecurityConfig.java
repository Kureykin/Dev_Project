package com.example.app.security.configurer;

import com.example.app.security.filters.JWTAuthorizationFilter;
import com.example.app.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${app.secret.key}")
    private String secret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager manager) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/v1/registration", "/user/v1/login", "/slicer/v1/url/redirect/{id}").permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(new JwtAuthenticationFilter(manager, secret), ExceptionTranslationFilter.class)
                .addFilterAfter(new JWTAuthorizationFilter(secret), JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate template) {
        return username -> {
            String sql = "select \"Username\", \"Password\" from \"Users\" where \"Username\" = ?;";

            var tmp =  template.query(sql, (rs, rowNum) ->
                            User.withUsername(rs.getString("Username"))
                                    .password(rs.getString("Password"))
                                    .authorities("ROLE_USER")
                                    .build(), username)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

            return tmp;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationM) {
        return authenticationM.getAuthenticationManager();
    }
}
