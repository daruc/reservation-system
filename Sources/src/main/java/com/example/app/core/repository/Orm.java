package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.example.app.homepage.UserModel;
import com.example.app.reservation.AvailableReservationModel;
import com.example.app.reservation.ReservationModel;
import com.example.app.settings.ResourceModel;
import com.example.app.settings.UsersGroupModel;

public enum Orm {
	INSTANCE;
	
	private Map<Class<?>, DataMap> map = new HashMap<>();
	
	Orm() {
		map.put(UserModel.class, usersMap());
		map.put(UsersGroupModel.class, groupsMap());
		map.put(ResourceModel.class, resourcesMap());
		map.put(ReservationModel.class, reservationsMap());
		map.put(AvailableReservationModel.class, availableReservationsMap());
	}
	
	public DataMap get(Class<?> domainModel) {
		return map.get(domainModel);
	}
	
	private DataMap usersMap() {
		
		return new DataMap()
			.setModelClass(UserModel.class)
			.setTableName("users")
			.addFieldColumnMap(int.class, "id", "id")
			.addFieldColumnMap(String.class, "login", "login")
			.addFieldColumnMap(String.class, "password", "password")
			.addFieldColumnMap(String.class, "name", "name")
			.addFieldColumnMap(String.class, "surname", "surname")
			.addFieldColumnMap(int.class, "accessLevel", "permissions");
	}
	
	private DataMap groupsMap() {
		return new DataMap()
			.setModelClass(UsersGroupModel.class)
			.setTableName("groups")
			.addFieldColumnMap(int.class, "id", "id")
			.addFieldColumnMap(String.class, "name", "name")
			.addFieldColumnMap(String.class, "description", "description");
	}
	
	private DataMap resourcesMap() {
		return new DataMap()
				.setModelClass(ResourceModel.class)
				.setTableName("resource_groups")
				.addFieldColumnMap(int.class, "id", "id")
				.addFieldColumnMap(String.class, "name", "name")
				.addFieldColumnMap(String.class, "description", "description");
	}
	
	private DataMap reservationsMap() {
		return new DataMap()
				.setModelClass(ReservationModel.class)
				.setTableName("reservations")
				.addFieldColumnMap(int.class, "id", "id")
				.addFieldColumnMap(String.class, "name", "name")
				.addFieldColumnMap(String.class, "description", "description")
				.addFieldColumnMap(int.class, "authorId", "author_id")
				.addFieldColumnMap(int.class, "groupId", "group_id")
				.addFieldColumnMap(int.class, "resourceId", "resource_id");
	}
	
	private DataMap availableReservationsMap() {
		return new DataMap()
				.setModelClass(AvailableReservationModel.class)
				.setTableName("made_reservations")
				.addFieldColumnMap(int.class, "id", "id")
				.addFieldColumnMap(String.class, "label", "label")
				.addFieldColumnMap(int.class, "reservationId", "reservation_id")
				.addFieldColumnMap(int.class, "userId", "user_id");
	}
	
}
