package com.example.app.core;

public class DomainModel {
	protected int id;
	
	public DomainModel() {
	}
	
	public DomainModel(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
