package com.example.app.reservation;

import com.example.app.core.DomainModel;
import com.example.app.core.valueobjects.Label;

public class AvailableReservationModel extends DomainModel {
	
	private Label label;
	private int reservationId;
	private int userId;
	
	private Boolean checked = Boolean.FALSE;

	public String getLabel() {
		return label.getLabel();
	}

	public void setLabel(String label) {
		if (label != null) {
			this.label = new Label(label);
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
