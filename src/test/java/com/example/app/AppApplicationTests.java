package com.example.app;

import com.example.app.db.entity.UrlData;
import com.example.app.db.entity.UserData;
import com.example.app.db.repository.SlicerRepository;
import com.example.app.db.repository.UserRepository;
import com.example.app.service.*;
import com.example.app.untils.exception.InvalidPasswordException;
import com.example.app.untils.exception.UserAlreadyExistException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
class AppApplicationTests {

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
	private SlicerRepository slicerRepo;
	@Autowired
	private SlicerServiceImp slicerService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserDataServiceImp userService;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	private final UserData testUser = new UserData("john",
			encoder.encode("QwEr12345"));

	private final UrlData testData = new UrlData("a14b2f31" ,"www.youtube.com", testUser);

	@BeforeEach
	void saveTestData() {
		userRepo.save(testUser);
		slicerRepo.save(testData);
	}

	@AfterEach
	void deleteTestData() {
		slicerRepo.deleteById(testData.getSlicedUrl());
	}

	@Test
	void sliceTest() {
		String result = slicerService.slice(testData.getUrl(), testUser.getUsername());

        Assertions.assertTrue(slicerRepo.existsById(result));

		slicerRepo.deleteById(result);
	}

	@Test
	void showDataTest() {

		List<UrlData> list = List.of(testData);

		List<UrlData> response = slicerService.showData(testUser.getUsername());

		Assertions.assertEquals(list, response);
	}

	@Test
	void editTest() {
		String newTrueUrl = "www.google.com";

		UrlData response = slicerService.edit(testData.getSlicedUrl(), newTrueUrl);

		Assertions.assertEquals(newTrueUrl, response.getUrl());
	}

	@Test
	void redirectTest() {

		String result = slicerService.redirect(testData.getSlicedUrl());

		Assertions.assertEquals(testData.getUrl(), result);
	}

	@Test
	void deleteTest() {

		UrlData result = slicerService.delete(testData.getSlicedUrl());

		Assertions.assertEquals(testData, result);
	}

	@Test
	void correctRegistrationTest(){
		String username = "alex";
		String password = "QwEr12345";


		String result = userService.newUser(username, password);

		Assertions.assertEquals(username, result);
	}

	@Test
	void invalidPasswordTest() {
		String username = "john";
		String password = "QWER12345";

		Assertions.assertThrows(InvalidPasswordException.class, () -> userService.newUser(username, password));
	}

	@Test
	void userExistTest(){
		String username = "john";
		String password = "QwEr12345";

		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.newUser(username, password));
	}

	@Test
	void findUserByNameTest() {
		String username = testUser.getUsername();

		UserData result = userService.findUserByName(username);

		Assertions.assertEquals(testUser, result);
	}

	@Test
	void getAllActiveUrlsTest() {
		List<UrlData> result = slicerService.showActiveData(testUser.getUsername());

		Assertions.assertEquals(List.of(testData), result);
	}


	@Mock
	private SlicerRepository testSliserRepo;

	@InjectMocks
	private SlicerServiceImp testSlicerService;

	@Test
	void getNonactiveUrl() {
		UrlData testUrl = new UrlData("fnvk214l" ,"www.youtube.com", testUser, false);

		when(testSliserRepo.findById(testUrl.getSlicedUrl())).thenReturn(Optional.of(testUrl));

		Assertions.assertThrows(ResponseStatusException.class, () -> testSlicerService.redirect(testUrl.getSlicedUrl()));
	}
}
