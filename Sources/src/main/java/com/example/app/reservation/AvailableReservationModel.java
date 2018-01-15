package com.example.app.reservation;

import com.example.app.core.DomainModel;

public class AvailableReservationModel extends DomainModel {
	
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
