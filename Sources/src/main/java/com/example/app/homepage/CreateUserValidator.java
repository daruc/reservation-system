package com.example.app.homepage;

import com.example.app.core.Validator;

public class CreateUserValidator implements Validator {
	private UserModel user;
	
	public CreateUserValidator(UserModel user) {
		this.user = user;
	}
	
	public boolean isValid() {
		if (user.getName() == null || user.getName() == "") {
			return false;
		}
		if (user.getPassword() == null || user.getPassword() == "") {
			return false;
		}
		if (user.getName() == null || user.getName() == "") {
			return false;
		}
		if (user.getSurname() == null || user.getSurname() == "") {
			return false;
		}
		return true;
	}
}
