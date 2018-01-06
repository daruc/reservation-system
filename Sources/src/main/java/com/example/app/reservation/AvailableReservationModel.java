package com.example.app.reservation;

public class AvailableReservationModel {
	
	private int id;
	private String label;
	private int reservationId;
	private int userId;
	
	private Boolean checked = Boolean.FALSE;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if (label != null) {
			this.label = label.trim();
		}
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public void setUserId(int id) {
		userId = id;
	}
	
	public int getUserId() {
		return userId;
	}
}
