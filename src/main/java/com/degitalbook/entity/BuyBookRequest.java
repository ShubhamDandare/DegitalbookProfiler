package com.degitalbook.entity;

public class BuyBookRequest {
	private String username;
	private String email;
	private Long bookid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBookid() {
		return bookid;
	}

	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}

}