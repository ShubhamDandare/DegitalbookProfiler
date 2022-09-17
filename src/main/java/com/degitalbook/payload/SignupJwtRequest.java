package com.degitalbook.payload;

import java.util.Set;
import java.util.Set;

import com.degitalbook.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class SignupJwtRequest {

	private String username;
	private String password;
	private String email;
	private Set<String> role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRole() {
		return role;
	}
	public void setRole(Set<String> role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "SignupJwtRequest [username=" + username + ", password=" + password + ", email=" + email + ", role="
				+ role + "]";
	}
	public SignupJwtRequest(String username, String password, String email, Set<String> role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	public SignupJwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
