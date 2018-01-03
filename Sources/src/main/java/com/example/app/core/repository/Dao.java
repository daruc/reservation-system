package com.example.app.core.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.homepage.LoginModel;
import com.example.app.homepage.UserModel;
import com.example.app.reservation.AvailableReservationModel;
import com.example.app.reservation.ReservationModel;
import com.example.app.settings.ResourceModel;
import com.example.app.settings.UsersGroupModel;

public class Dao {
	
	private static final Logger logger = LoggerFactory.getLogger(Dao.class);
	
	private DataSource dataSource;
	private Repository repository;

	@Autowired
	public Dao(DataSource dataSource) {
		this.dataSource = dataSource;
		repository = new Repository(dataSource);
	}
	
	public boolean createUser(UserModel userFormModel) {
		String sql = new StringBuilder()
				.append("insert into users(login, password, name, surname, permissions) values('")
				.append(userFormModel.getLogin())
				.append("', '")
				.append(userFormModel.getPassword())
				.append("', '")
				.append(userFormModel.getName())
				.append("', '")
				.append(userFormModel.getSurname())
				.append("', 0);")
				.toString();
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			
			return con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			logger.error("Cannot create a new user.", e);
			return false;
		}
	}
	
	public boolean checkUser(LoginModel loginModel) {
		String sql = new StringBuilder()
				.append("select count(*) from users where login = '")
				.append(loginModel.getLogin())
				.append("' and password = '")
				.append(loginModel.getPassword())
				.append("';")
				.toString();
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			return count == 1;
		} catch (SQLException e) {
			logger.error("Cannot check the user with login " + loginModel.getLogin() + ".", e);
		}
		return false;
	}

	public UserModel getUser(String login) {
		if (login == null) {
			return null;
		}
		
		QueryObject<UserModel> queryObject = repository.queryObjectBuilder(UserModel.class)
			.addCriteria("login", Criteria.SqlOperator.EQUAL, login)
			.build();
		
		try {
			return queryObject.execute().get(0);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Cannot retrieve user with login " + login + " from database.", e);
			return null;
		}
	}
	
	public UserModel getUser(int id) {
		QueryObject<UserModel> queryObject = repository.queryObjectBuilder(UserModel.class)
				.addCriteria("id", Criteria.SqlOperator.EQUAL, id)
				.build();
		
		try {
			return queryObject.execute().get(0);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Cannot retrieve user with ID " + id + " from database.", e);
			return null;
		}
		
	}
	
	public List<UserModel> getAllUsers() {
		QueryObject<UserModel> queryObject = repository.queryObjectBuilder(UserModel.class)
				.build();
		
		return queryObject.execute();
	}
	
	public boolean deleteUser(int userId) {
		boolean result = true;
		String sql = "delete from users where id = " + userId + ";";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot delete user with ID " + userId + " from database.", e);
		}
		return result;
	}
	
	public boolean createUsersGroup(UsersGroupModel usersGroup) {
		String sql = new StringBuilder()
				.append("insert into groups(name, description) values('")
				.append(usersGroup.getName())
				.append("', '")
				.append(usersGroup.getDescription())
				.append("');")
				.toString();
		
		logger.info(sql);
		try {
			return dataSource.getConnection().prepareStatement(sql).execute();
		} catch (SQLException e) {
			logger.error("Cannot create a new Users Group.", e);
			return false;
		}
	}
	
	public List<UsersGroupModel> getAllUsersGroups() {
		QueryObject<UsersGroupModel> queryObject = repository.queryObjectBuilder(UsersGroupModel.class)
				.build();
		return queryObject.execute();
	}
	
	public UsersGroupModel getUserGroup(int id) {
		
		QueryObject<UsersGroupModel> queryObject = repository.queryObjectBuilder(UsersGroupModel.class)
				.addCriteria("id", Criteria.SqlOperator.EQUAL, id)
				.build();
		
		try {
			return queryObject.execute().get(0);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Cannot retrieve Users Group with ID " + id + " from database.", e);
			return null;
		}
	}
	
	public List<UserModel> getAllUsersWithoutGroup() {
		List<UserModel> usersList = new ArrayList<>();
		String sql = "select id, login, password, name, surname from users"
				+ " where id not in (select distinct user_id from user_group)";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel();
				user.setId(resultSet.getInt("id"));
				user.setLogin(resultSet.getString("login"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setPassword(resultSet.getString("password"));
				usersList.add(user);
			}
		} catch (SQLException e) {
			logger.error("Cannot retrieve all users without group from database.", e);
		}
		return usersList;
	}
	
	public List<UserModel> getUsers(int groupId) {
		List<UserModel> usersList = new ArrayList<>();
		String sql = "select u.id, u.login, u.password, u.name, u.surname"
				+ " from users as u inner join user_group as ug on u.id = ug.user_id"
				+ " where ug.group_id = " + groupId + ";";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel();
				user.setId(resultSet.getInt("id"));
				user.setLogin(resultSet.getString("login"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setPassword(resultSet.getString("password"));
				usersList.add(user);
			}
		} catch(SQLException e) {
			logger.error("Cannot retrieve users with Group ID " + groupId + " from database.", e);
		}
		return usersList;
	}
	
	public boolean addUsersToGroup(List<Integer> userIds, int groupId) {
		boolean result = true;
		try (Connection con = dataSource.getConnection()) {
			
			for (Integer userId : userIds) {
				String sql = new StringBuilder("insert into user_group(user_id, group_id) values (")
					.append(userId)
					.append(", ")
					.append(groupId)
					.append(");")
					.toString();
				
				logger.info(sql);
				con.prepareStatement(sql).execute();
			}
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot add users to group with ID " + groupId + ".", e);
		}
		return result;
	}
	
	public boolean createResource(ResourceModel resource) {
		boolean result = true;
		String sql = new StringBuilder("insert into resource_groups(name, description) values('")
				.append(resource.getName())
				.append("', '")
				.append(resource.getDescription())
				.append("');")
				.toString();
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot create Resource " + resource.getName() + ".", e);
		}
		return result;
	}
	
	public List<ResourceModel> getAllResources() {
		QueryObject<ResourceModel> queryObject = repository.queryObjectBuilder(ResourceModel.class)
				.build();
		return queryObject.execute();
	}
	
	public ResourceModel getResource(int id) {
		QueryObject<ResourceModel> queryObject = repository.queryObjectBuilder(ResourceModel.class)
				.addCriteria("id", Criteria.SqlOperator.EQUAL, id)
				.build();
		
		return queryObject.execute().get(0);
	}
	
	public boolean createReservation(ReservationModel reservation) {
		boolean result = true;
		String sql = new StringBuilder(
				"insert into reservations(name, description, group_id, author_id, resource_id) values('")
				.append(reservation.getName())
				.append("', '")
				.append(reservation.getDescription())
				.append("', ")
				.append(reservation.getGroupId())
				.append(", ")
				.append(reservation.getAuthorId())
				.append(", ")
				.append(reservation.getResourceId())
				.append(");")
				.toString();
		
		String sqlId = "select currval(pg_get_serial_sequence('reservations', 'id'));";
		
		logger.info(sql);
		logger.info(sqlId);
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql).execute();
			ResultSet resultSet = con.prepareStatement(sqlId).executeQuery();
			resultSet.next();
			reservation.setId(resultSet.getInt(1));
		} catch(SQLException e) {
			result = false;
			logger.error("Cannot create reservation " + reservation.getName() + ".", e);
		}
		return result;
	}
	
	public boolean createAvailableReservation(AvailableReservationModel reservation) {
		boolean result = true;
		String sql = "insert into made_reservations(label, reservation_id)"
				+ " values(?, ?);";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, reservation.getLabel());
			stm.setInt(2, reservation.getReservationId());
			stm.execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot create Available Reservation " + reservation.getLabel() + ".", e);
		}
		return result;
	}
	
	public List<ReservationModel> getReservationsForUser(int userId) {
		List<ReservationModel> reservations = new ArrayList<>();
		String sql = "select r.id, r.name"
				+ " from reservations as r inner join user_group as ug on r.group_id = ug.group_id"
				+ " where ug.user_id = ?;";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, userId);
			ResultSet resultSet = stm.executeQuery();
			while (resultSet.next()) {
				ReservationModel reservation = new ReservationModel();
				reservation.setId(resultSet.getInt("id"));
				reservation.setName(resultSet.getString("name"));
				reservations.add(reservation);
			}
		} catch (SQLException e) {
			logger.error("Cannot retrieve reservations for user with ID " + userId + " from database.", e);
		}
		return reservations;
	}
	
	public ReservationModel getReservation(int reservationId) {
		QueryObject<ReservationModel> queryObject = repository.queryObjectBuilder(ReservationModel.class)
				.addCriteria("id", Criteria.SqlOperator.EQUAL, reservationId)
				.build();
		
		return queryObject.execute().get(0);
	}
	
	public List<AvailableReservationModel> getAvailableReservations(int reservationId) {

		QueryObject<AvailableReservationModel> queryObject =
				repository.queryObjectBuilder(AvailableReservationModel.class)
				.addCriteria("reservationId", Criteria.SqlOperator.EQUAL, reservationId)
				.addCriteria("userId", Criteria.SqlOperator.IS, null)
				.build();
		
		return queryObject.execute();
	}
	
	public boolean makeReservation(int userId, int availableReservationId) {
		boolean result = true;
		String sql = "update made_reservations set user_id = ? where id = ?;";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, userId);
			stm.setInt(2, availableReservationId);
			stm.execute();
		} catch (SQLException e) {
			result = false;
			logger.error("User " + userId + " cannot reserve Available Reservation "
					+ availableReservationId + ".", e);
		}
		return result;
	}
	
	public List<AvailableReservationModel> getMadeReservations(int reservationId, int userId) {
		
		QueryObject<AvailableReservationModel> queryObject =
				repository.queryObjectBuilder(AvailableReservationModel.class)
				.addCriteria("reservationId", Criteria.SqlOperator.EQUAL, reservationId)
				.addCriteria("userId", Criteria.SqlOperator.EQUAL, userId)
				.build();
		
		return queryObject.execute();
	}
	
	public AvailableReservationModel getMadeReservation(int availableReservationId) {
		
		QueryObject<AvailableReservationModel> queryObject = 
				repository.queryObjectBuilder(AvailableReservationModel.class)
				.addCriteria("id", Criteria.SqlOperator.EQUAL, availableReservationId)
				.build();
		
		return queryObject.execute().get(0);
	}
	
	public boolean cancelReservation(int availableReservationId) {
		boolean result = true;
		String sql = "update made_reservations set user_id = null where id = "
				+ availableReservationId + ";";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot cancel Available Reservation with ID " + availableReservationId + ".", e);
		}
		
		return result;
	}
	
	public boolean deleteReservation(int reservationId) {
		boolean result = true;
		String sql1 = "delete from made_reservations where reservation_id = " + reservationId + ";";
		String sql2 = "delete from reservations where id = " + reservationId + ";";
		
		logger.info(sql1);
		logger.info(sql2);
		
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql1).execute();
			con.prepareStatement(sql2).execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot delete reservation with ID " + reservationId + ".", e);
		}
		
		return result;
	}
	
	public boolean deleteResource(int resourceId) {
		if (!isResourceFree(resourceId)) {
			return false;
		}
		boolean result = true;
		
		String sql = "delete from resource_groups where id = " + resourceId + ";";
		
		logger.info(sql);
		try (Connection con = dataSource.getConnection()) {
			con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			result = false;
			logger.error("Cannot delete resource with ID " + resourceId + ".", e);
		}
		
		return result;
	}
	
	private boolean isResourceFree(int resourceId) {
		
		QueryObject<ReservationModel> queryObject = repository.queryObjectBuilder(ReservationModel.class)
				.addCriteria("resourceId", Criteria.SqlOperator.EQUAL, resourceId)
				.build();
		return queryObject.execute().isEmpty();
	}
}
