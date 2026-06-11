package com.example.app;

import com.example.app.db.entity.UrlData;
import com.example.app.db.entity.UserData;
import com.example.app.db.repository.SlicerRepository;
import com.example.app.db.repository.UserRepository;
import com.example.app.requests.EditRequest;
import com.example.app.service.SlicerService;
import com.example.app.service.SlicerServiceImp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.tags.EditorAwareTag;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ControllerTests {

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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SlicerRepository slicerRepo;
    @Autowired
    private SlicerService slicerService;

    @Value("${app.secret.key}")
    private String SECRET;

    private String testUsername;
    private Duration tokenTtl;
    private String testToken;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserData testUser = new UserData("john",
            encoder.encode("QwEr12345"));
    private final UrlData testData = new UrlData("a14b2f31" ,"www.youtube.com", testUser);

    @BeforeEach
    void saveTestData() {
        userRepo.save(testUser);
        slicerRepo.save(testData);

         testUsername = testUser.getUsername();
         tokenTtl = Duration.ofHours(1);

         testToken = Jwts.builder()
                .setSubject(testUsername)
                .setExpiration(Date.from(Instant.now().plus(tokenTtl)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    @AfterEach
    void deleteTestData() {
        slicerRepo.delete(testData);
         testUsername = null;
         tokenTtl = null;
         testToken = null;
    }

    @Test
    void invalidEditUrlTest() {
        Map<String,String> request = Map.of(
                "id", testData.getSlicedUrl(),
                "newUrl", ""
        );

        try {
            mockMvc.perform(put("/slicer/v1/url")
                            .header("Authorization", "Bearer " + testToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request.toString()))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void editControllerTest() {
        String newTrueUrl = "https://github.com/";
        EditRequest request = new EditRequest();
        request.setId(testData.getSlicedUrl());
        request.setNewUrl(newTrueUrl);

        try {
            mockMvc.perform(put("/slicer/v1/url")
                            .header("Authorization", "Bearer " + testToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request.toString()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void activeUrlsListingTest() {

        try {
            mockMvc.perform(get("/slicer/v1/list/active")
                            .header("Authorization", "Bearer " + testToken))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteControllerTest() {

        try {
            mockMvc.perform(delete("/slicer/v1/url/{id}", testData.getSlicedUrl())
                            .header("Authorization", "Bearer " + testToken))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
