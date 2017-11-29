package com.example.app.reservation;

import java.util.List;

public class AvailableReservationsWrapper {
	private List<AvailableReservationModel> availableReservations;

	public List<AvailableReservationModel> getAvailableReservations() {
		return availableReservations;
	}

	public void setAvailableReservations(List<AvailableReservationModel> availableReservations) {
		this.availableReservations = availableReservations;
	}
}
