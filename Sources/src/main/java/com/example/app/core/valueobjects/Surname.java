package com.example.app.core.valueobjects;

public class Surname {
	private final String surname;
	
	public Surname(String surname) {
		if (surname == null) {
			throw new IllegalArgumentException("Surname cannot be empty.");
		}
		
		if (surname.equals("")) {
			this.surname = surname;
		} else {
			String surnameTrim = surname.trim();
			this.surname = Character.toUpperCase(surnameTrim.charAt(0)) + surnameTrim.substring(1);
		}
	}
	
	public String getSurname() {
		return surname;
	}
	
	@Override
	public boolean equals(Object other) {
		return surname.equals(other);
	}
	
	@Override
	public int hashCode() {
		return surname.hashCode();
	}
	
	private static final Surname EMPTY = new Surname("");
	public static Surname getEmpty() {
		return EMPTY;
	}
}
