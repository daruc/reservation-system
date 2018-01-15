package com.example.app.core.valueobjects;

public final class Name {
	private final String name;
	
	public Name(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be empty.");
		}
		String nameTrim = name.trim();
		this.name = Character.toUpperCase(nameTrim.charAt(0)) + nameTrim.substring(1);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		return name.equals(other);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
