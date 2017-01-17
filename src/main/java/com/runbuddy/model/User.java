package com.runbuddy.model;

public class User {
	//private int id;
	private String username;
	private String password;

	// 无参构造
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}



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

}
