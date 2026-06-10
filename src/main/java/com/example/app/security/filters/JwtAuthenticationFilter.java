package com.example.app.security.filters;

import com.example.app.requests.LoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String secret;

    private Duration tokenTtl = Duration.ofHours(1);

    private AuthenticationManager manager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String secret) {
        this.manager = authenticationManager;
        this.secret = secret;
        setFilterProcessesUrl("/user/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest;
        try {
            loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        System.out.println(password);

        return manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

            String token = Jwts.builder()
                    .setSubject(((UserDetails)authResult.getPrincipal()).getUsername())
                    .setExpiration(Date.from(Instant.now().plus(tokenTtl)))
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
            response.addHeader("Authorization", "Bearer " + token);
    }
}
