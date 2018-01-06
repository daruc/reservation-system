package com.example.app.settings;

import com.example.app.core.Validator;

public class CreateUsersGroupValidator implements Validator {
	
	private UsersGroupModel usersGroup;
	
	public CreateUsersGroupValidator(UsersGroupModel usersGroup) {
		this.usersGroup = usersGroup;
	}

	@Override
	public boolean isValid() {
		if (usersGroup.getName() == null || usersGroup.getName() == "") {
			return false;
		}
		return true;
	}

}
