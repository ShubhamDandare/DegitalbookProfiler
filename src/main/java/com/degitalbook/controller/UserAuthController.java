package com.degitalbook.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.degitalbook.Repository.DegitalbookRoleRepository;
import com.degitalbook.Repository.DegitalbookUserRepository;
import com.degitalbook.Service.UserDetailsImpl;
import com.degitalbook.entity.DegitalbookUser;
import com.degitalbook.entity.ERole;
import com.degitalbook.entity.Role;
import com.degitalbook.payload.JwtResponse;
import com.degitalbook.payload.LoginJwtRequest;
import com.degitalbook.payload.MessageResponse;
import com.degitalbook.payload.SignupJwtRequest;
import com.degitalbook.utils.JwtUtils;

@CrossOrigin
@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	DegitalbookUserRepository userRepository;

	@Autowired
	DegitalbookRoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginJwtRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupJwtRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		DegitalbookUser user = new DegitalbookUser(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		System.out.println("STR ROLE:" + strRoles);

		if (strRoles == null) {
			Role ReaderRole = roleRepository.findByName(ERole.ROLE_READER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(ReaderRole);
		} else {
			strRoles.forEach(role -> {

				switch (role) {
				case "ROLE_AUTHOR":
					Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add( authorRole);

					break;
				
				default:
					Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(readerRole);
				}
			});
		}

		user.setRoles(roles);
		DegitalbookUser save = userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse(save.toString()+": User registered successfully!"));
	}

}
