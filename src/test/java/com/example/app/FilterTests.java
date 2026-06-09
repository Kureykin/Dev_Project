package com.example.app;

import com.example.app.db.entity.UserData;
import com.example.app.db.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class FilterTests {

    @Container
    private static PostgreSQLContainer
            POSTGRES = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("testname")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepo;

    @Value("${app.secret.key}")
    private String SECRET;


    private final UserData testUser = new UserData("john",
            encoder.encode("QwEr12345"));

    @BeforeEach
    void saveTestData() {
        userRepo.save(testUser);
    }

    @Test
    void successfulLoginTest() {
        Map<String,String> request = Map.of(
                "username", "john",
                "password", "QwEr12345"
        );

        try {
            mockMvc.perform(post("/user/v1/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(header().exists("Authorization"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void successAuthorization() {
        String testSECRET = SECRET;
        Duration tokenTtl = Duration.ofHours(1);
        String testUsername = testUser.getUsername();

        String testToken = Jwts.builder()
                .setSubject(testUsername)
                .setExpiration(Date.from(Instant.now().plus(tokenTtl)))
                .signWith(SignatureAlgorithm.HS256, testSECRET)
                .compact();


        try {
            mockMvc.perform(get("/slicer/v1/list")
                            .header("Authorization", "Bearer " + testToken))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
