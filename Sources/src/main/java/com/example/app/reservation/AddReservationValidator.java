package com.example.app.reservation;

import com.example.app.core.Validator;

public class AddReservationValidator implements Validator {
	private ReservationModel reservation;
	
	public AddReservationValidator(ReservationModel reservation) {
		this.reservation = reservation;
	}

	@Override
	public boolean isValid() {
		if (reservation.getName() == null || reservation.getName() == "") {
			return false;
		}
		return true;
	}

}
