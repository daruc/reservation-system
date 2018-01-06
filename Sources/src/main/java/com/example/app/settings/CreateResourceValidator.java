package com.example.app.settings;

import com.example.app.core.Validator;

public class CreateResourceValidator implements Validator {
	
	private ResourceModel resource;
	
	public CreateResourceValidator(ResourceModel resource) {
		this.resource = resource;
	}

	@Override
	public boolean isValid() {
		if (resource.getName() == null || resource.getName() == "") {
			return false;
		}
		return true;
	}

}
