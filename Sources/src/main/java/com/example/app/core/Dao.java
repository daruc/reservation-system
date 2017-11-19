package com.example.app.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.homepage.LoginModel;
import com.example.app.homepage.UserModel;

public class Dao {
	private DataSource dataSource;

	@Autowired
	public Dao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean createUser(UserModel userFormModel) {
		String sql = new StringBuilder()
				.append("insert into users(login, password, name, surname) values('")
				.append(userFormModel.getLogin())
				.append("', '")
				.append(userFormModel.getPassword())
				.append("', '")
				.append(userFormModel.getName())
				.append("', '")
				.append(userFormModel.getSurname())
				.append("');")
				.toString();
		
		try {
			return dataSource.getConnection().prepareStatement(sql)
			.execute();
		} catch (SQLException e) {
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
		try {
			ResultSet resultSet = dataSource.getConnection().prepareStatement(sql).executeQuery();
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
		try {
			ResultSet resultSet = dataSource.getConnection().prepareStatement(sql).executeQuery();
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
}
