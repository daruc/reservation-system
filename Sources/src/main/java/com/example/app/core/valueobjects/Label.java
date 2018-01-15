package com.example.app.core.valueobjects;

public class Label {
	private final String label;
	
	public Label(String label) {
		if (label == null) {
			throw new IllegalArgumentException("Label cannot be null.");
		}
		this.label = label.trim();
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public boolean equals(Object object) {
		return label.equals(label);
	}
	
	@Override
	public int hashCode() {
		return label.hashCode();
	}
	
	private static final Label EMPTY = new Label("");
	public static Label getEmpty() {
		return EMPTY;
	}
}
