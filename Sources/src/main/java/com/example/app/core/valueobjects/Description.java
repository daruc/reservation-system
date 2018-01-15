package com.example.app.core.valueobjects;

public final class Description {
	private final String description;
	
	public Description(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null.");
		}
		this.description = description.trim();
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public boolean equals(Object object) {
		return description.equals(description);
	}
	
	@Override
	public int hashCode() {
		return description.hashCode();
	}
}
