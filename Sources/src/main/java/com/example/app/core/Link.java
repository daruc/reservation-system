package com.example.app.core;

public class Link {
	public String address;
	public String description;
	
	public Link() {
		
	}
	
	public Link(String address, String description) {
		this.address = address;
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
