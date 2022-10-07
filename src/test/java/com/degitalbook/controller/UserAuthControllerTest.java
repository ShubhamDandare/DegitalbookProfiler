package com.degitalbook.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import com.degitalbook.Service.UserDetailsImpl;
import com.degitalbook.payload.LoginJwtRequest;

@SpringBootTest
public class UserAuthControllerTest {
	
	@InjectMocks
	public UserAuthController usercontroller;
	
	@Mock
	public AuthenticationManager authenticationManager;
	
	@Mock
	public UserDetailsImpl userdetails;
	
	@Test
	public void authenticateUser() {
		
		LoginJwtRequest jwtRequest=new LoginJwtRequest();
		jwtRequest.setUsername("12345asd@gmail.com");
		jwtRequest.setPassword("12345asd");
		
//		when(userdetails.)
		
	}

}
