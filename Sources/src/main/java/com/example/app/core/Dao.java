package com.example.app.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.homepage.LoginModel;
import com.example.app.homepage.UserModel;
import com.example.app.settings.UsersGroupModel;

public class Dao {
	private DataSource dataSource;

	@Autowired
	public Dao(DataSource dataSource) {
		this.dataSource = dataSource;
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
		
		try (Connection con = dataSource.getConnection()) {
			return con.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
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
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public UserModel getUser(String login) {
		String sql = new StringBuilder()
				.append("select password, name, surname from users where login = '")
				.append(login)
				.append("';")
				.toString();
		try (Connection con = dataSource.getConnection()){
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			resultSet.next();
			UserModel user = new UserModel(this);
			user.setLogin(login);
			user.setPassword(resultSet.getString("password"));
			user.setName(resultSet.getString("name"));
			user.setSurname(resultSet.getString("surname"));
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createUsersGroup(UsersGroupModel usersGroup) {
		String sql = new StringBuilder()
				.append("insert into groups(name, description) values('")
				.append(usersGroup.getName())
				.append("', '")
				.append(usersGroup.getDescription())
				.append("');")
				.toString();
		
		try {
			return dataSource.getConnection().prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<UsersGroupModel> getAllUsersGroups() {
		String sql = "select id, name, description from groups;";
		List<UsersGroupModel> usersGroupsList = new ArrayList<>();
		try (Connection con = dataSource.getConnection()){
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				UsersGroupModel usersGroup = new UsersGroupModel(this);
				usersGroup.setId(resultSet.getInt("id"));
				usersGroup.setName(resultSet.getString("name"));
				usersGroup.setDescription(resultSet.getString("description"));
				usersGroupsList.add(usersGroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersGroupsList;
	}
	
	public UsersGroupModel getUserGroup(int id) {
		String sql = new StringBuilder("select name, description from groups where id = ")
				.append(Integer.toString(id))
				.append(" ;")
				.toString();
		
		UsersGroupModel usersGroup = null;
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			resultSet.next();
			usersGroup = new UsersGroupModel(this);
			usersGroup.setId(id);
			usersGroup.setName(resultSet.getString("name"));
			usersGroup.setDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersGroup;
	}
	
	public List<UserModel> getAllUsersWithoutGroup() {
		List<UserModel> usersList = new ArrayList<>();
		String sql = "select id, login, password, name, surname from users"
				+ " where id not in (select distinct user_id from user_group)";
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel(this);
				user.setId(resultSet.getInt("id"));
				user.setLogin(resultSet.getString("login"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setPassword(resultSet.getString("password"));
				usersList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}
	
	public List<UserModel> getUsers(int groupId) {
		List<UserModel> usersList = new ArrayList<>();
		String sql = "select u.id, u.login, u.password, u.name, u.surname"
				+ " from users as u inner join user_group as ug on u.id = ug.user_id"
				+ " where ug.group_id = " + groupId + ";";
		try (Connection con = dataSource.getConnection()) {
			ResultSet resultSet = con.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				UserModel user = new UserModel(this);
				user.setId(resultSet.getInt("id"));
				user.setLogin(resultSet.getString("login"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setPassword(resultSet.getString("password"));
				usersList.add(user);
			}
		} catch(SQLException e) {
			e.printStackTrace();
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
				con.prepareStatement(sql).execute();
			}
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
}
