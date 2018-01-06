package com.example.app.reservation;

import com.example.app.core.Validator;

public class AddAvailableReservationValidator implements Validator {
	private AvailableReservationModel availableReservation;
	
	public AddAvailableReservationValidator(AvailableReservationModel availableReservation) {
		this.availableReservation = availableReservation;
	}

	@Override
	public boolean isValid() {
		if (availableReservation.getLabel() == null || availableReservation.getLabel() == "") {
			return false;
		}
		return true;
	}

}
