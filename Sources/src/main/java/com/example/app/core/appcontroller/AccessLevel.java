package com.example.app.core.appcontroller;

public enum AccessLevel {
	EVERYONE(0), LOGGED_IN(1), ADMIN(2);
	
	private int level;
	
	AccessLevel(int level) {
		this.level = level;
	}
	
	public int toInt() {
		return level;
	}
	
	public static AccessLevel fromInt(int level) {
		for (AccessLevel accessLevel : values()) {
			if (accessLevel.toInt() == level) {
				return accessLevel;
			}
		}
		
		throw new IllegalArgumentException(level + " access level does not exist.");
	}
}
