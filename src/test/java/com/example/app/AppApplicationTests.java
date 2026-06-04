package com.example.app;

import com.example.app.entity.UrlData;
import com.example.app.entity.UserData;
import com.example.app.exception.InvalidPasswordException;
import com.example.app.exception.NoUserException;
import com.example.app.exception.UserAlreadyExistException;
import com.example.app.exception.WrongPasswordException;
import com.example.app.repository.SlicerRepository;
import com.example.app.repository.UserRepository;
import com.example.app.requests.EditRequest;
import com.example.app.response.EditResponse;
import com.example.app.response.NewUrlResponse;
import com.example.app.response.ShowUrlResponse;
import com.example.app.service.SlicerServiceImp;
import com.example.app.service.UserDataServiceImp;
import com.example.app.token.JWTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AppApplicationTests {

	@Mock
	private SlicerRepository slicerRepository;
	@InjectMocks
	private SlicerServiceImp slicerService;


	@Test
	void sliceTest() {

		String url = "www.youtube.com";

		NewUrlResponse response = slicerService.slice(url);

        Assertions.assertNotNull(response);
	}
	@Test
	void showDataTest() {

		List<UrlData> list = List.of();

		when(slicerRepository.findAll()).thenReturn(list);

		ShowUrlResponse response = slicerService.showData();

		Assertions.assertEquals(list, response.getDataList());
	}
	@Test
	void editTest() {
		EditRequest request = new EditRequest();
		request.setId("local:8080/slicer/1");
		request.setNewUrl("asasdasdsadadasdewd");

		UrlData data = new UrlData("rfegjnedfgiojfedgio");

		when(slicerRepository.findById(request.getId())).thenReturn(Optional.of(data));

		EditResponse response = slicerService.edit(request);

		Assertions.assertEquals(request.getNewUrl(), response.getData().getUrl());
	}
	@Test
	void redirectTest() {
		String url = "www.youtube.com";
		UrlData data = new UrlData(url);

		when(slicerRepository.findById(data.getSlicedUrl())).thenReturn(Optional.of(data));

		String result = slicerService.redirect(data.getSlicedUrl());

		Assertions.assertEquals(url, result);
	}
	@Test
	void deleteTest() {
		String url = "www.youtube.com";
		UrlData data = new UrlData(url);

		when(slicerRepository.findById(data.getSlicedUrl())).thenReturn(Optional.of(data));

		UrlData result = slicerService.delete(data.getSlicedUrl());

		Assertions.assertEquals(data, result);
	}

	@Mock
	private UserRepository userRepo;
	@InjectMocks
	private UserDataServiceImp userService;

	@Test
	void correctRegistrationTest(){
		String username = "john";
		String password = "QwEr12345";
		UserData user = new UserData(username, password);

		when(userRepo.existsById(username)).thenReturn(false);

		String result = userService.newUser(username, password);

		Assertions.assertEquals(new JWTService().compile(username), result);
	}
	@Test
	void invalidPasswordTest() {
		String username = "john";
		String password = "QWER12345";

		Assertions.assertThrows(InvalidPasswordException.class, () -> userService.newUser(username, password));
	}
	@Test
	void useExistTest(){
		String username = "john";
		String password = "QwEr12345";

		when(userRepo.existsById(username)).thenReturn(true);

		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.newUser(username, password));
	}

	@Test
	void correctAuthorisationTest() {
		String username = "john";
		String password = "QwEr12345";
		UserData user = new UserData(username, password);

		when(userRepo.findById(username)).thenReturn(Optional.of(user));

		String result = userService.authorisation(username, password);

		Assertions.assertEquals(new JWTService().compile(username), result);
	}
	@Test
	void wrongPasswordTest() {
		String username = "john";
		String password = "QwEr12345";
		String wrongPassword = "1111111111";
		UserData user = new UserData(username, password);

		when(userRepo.findById(username)).thenReturn(Optional.of(user));

		Assertions.assertThrows(WrongPasswordException.class, () ->  userService.authorisation(username, wrongPassword));
	}
	@Test
	void NoUserTest() {
		String username = "john";
		String password = "QwEr12345";
		UserData user = new UserData(username, password);

		when(userRepo.findById(username)).thenReturn(Optional.empty());

		Assertions.assertThrows(NoUserException.class, () ->  userService.authorisation(username, password));
	}

	JWTService jwtService = new JWTService();

	@Test
	void compileTest() {
		String username = "john";

		String result = jwtService.compile(username);

        Assertions.assertNotSame(username, result);
	}
}
